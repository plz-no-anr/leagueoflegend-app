package com.psg.leagueoflegend_app.view.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.psg.domain.model.Profile
import com.psg.domain.model.Summoner
import com.psg.leagueoflegend_app.R
import com.psg.leagueoflegend_app.base.BaseActivity
import com.psg.leagueoflegend_app.databinding.ActivityMainBinding
import com.psg.leagueoflegend_app.utils.AppLogger
import com.psg.leagueoflegend_app.utils.NetworkUtils
import com.psg.leagueoflegend_app.view.search.SearchActivity
import com.psg.leagueoflegend_app.view.spectator.SpectatorActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_settingkey.*
import kotlinx.android.synthetic.main.header_navi.view.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>(R.layout.activity_main),
    NavigationView.OnNavigationItemSelectedListener {
    override val TAG: String = MainActivity::class.java.simpleName
    override val viewModel: MainViewModel by viewModels()
    private val adapter = MainAdapter()

    private var list: List<Summoner> = listOf()
    private var profile: Profile = Profile("", "", "")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initViewModel()
        setToolbar(binding.toolbar)
        checkNetwork()
        initView()
        setRv()
        setObserve()
        setEventFlow()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.search -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.option -> {
                binding.dlMain.openDrawer(Gravity.RIGHT)
                if (profile.icon != "") {
                    binding.nvMain.tv_name.text = profile.name
                    binding.nvMain.tv_level.text = "LV: ${profile.level}"
                    viewModel.bindImage(binding.nvMain.iv_image, profile.icon)
                    AppLogger.p("아이콘:${profile.icon}")
                } else {
                    binding.nvMain.tv_name.text = "이름"
                    binding.nvMain.tv_level.text = "LV: "
                    viewModel.bindImage(
                        binding.nvMain.iv_image,
                        "http://ddragon.leagueoflegends.com/cdn/11.24.1/img/profileicon/29.png"
                    )
                }
                true // 드로어 레이아웃 추가
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onBackPressed() {
        if (binding.dlMain.isDrawerOpen(Gravity.RIGHT)) {
            binding.dlMain.closeDrawer(Gravity.RIGHT)
        } else {
            super.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.summonerUpdate()
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        binding.viewModel = viewModel

        binding.slMain.setOnRefreshListener {
            refresh(list)
        }
        binding.tvDeleteAll.setOnClickListener {
//            viewModel.deleteAll()
            makeToast("전체 삭제되었습니다.")
        }

        binding.dlMain.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)

        binding.nvMain.setNavigationItemSelectedListener(this)

    }


    override fun setObserve() {
        viewModel.summonerList.observe(this) {
            if (it != null) {
                list = it
                refresh(it)
            } else {
                AppLogger.p("리스트 null")
            }
        }

        viewModel.profile.observe(this) {
            profile = if (it != null) {
                it
            } else {
                AppLogger.p("프로필 null")
                Profile("", "", "")
            }
        }

        viewModel.league.observe(this) {
            if (it != null) {
                when (it.code) {
//                    0 -> makeToast("승급전 진행중")
//                    1 -> makeToast("갱신 성공")
//                    2 -> makeToast("이번 시즌 솔로랭크 전적이 없거나\n 배치가 끝나지 않았습니다.")
//                    3 -> makeToast("이번 시즌 전적이 존재하지 않습니다.")
                    401 -> makeToast("토큰이 인증되지 않았습니다.")
                    403 -> makeToast("토큰이 만료되었습니다.")
                    404 -> makeToast("존재하지 않는 아이디입니다.")
                    429 -> AppLogger.p("너무 많은 요청")
                    else -> makeToast("이번 시즌 전적이 존재하지 않습니다.")
                }
            }
        }
    }


    private fun checkNetwork() {
        NetworkUtils.getNetworkStatus().observe(this) { isConnected ->
            if (!isConnected) makeToast("인터넷이 연결되어 있지 않습니다.")

        }
    }

    private fun refresh(list: List<Summoner>) {
        viewModel.refresh(list)
        viewModel.isRefresh.observe(this) {
            if (it) {
                binding.slMain.isRefreshing = false
            }
        }

    }

    override fun setRv() {
        adapter.setHasStableIds(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        // 리사이클러뷰 역순 정렬
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        binding.rvMain.layoutManager = layoutManager
        binding.rvMain.adapter = adapter
        viewModel.summonerList.observe(this) {
            if (it != null) {
                adapter.setData(it)
                AppLogger.p("Main: null이 아님")
            } else {
                AppLogger.p("Main: db값 null")
            }
        }

        adapter.setOnItemClickListener(object : MainAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: Summoner, pos: Int) {
                when (v.id) {
                    R.id.iv_delete -> {
                        viewModel.deleteSummoner(data)
                        AppLogger.p("아이템삭제")
                        makeToast("삭제 성공")
                        viewModel.summonerUpdate()
                    }
                    R.id.iv_addProfile -> {
                        viewModel.insertProfile(Profile(data.name, data.level, data.icon))
                        AppLogger.p("프로필변경")
                        makeToast("프로필 변경 성공")
                        viewModel.profileUpdate()
                    }
                    R.id.ll_spectator -> {
                        if (data.isPlaying) {
                            val intent = Intent(this@MainActivity, SpectatorActivity::class.java)
                            intent.putExtra("name", data.name)
                            startActivity(intent)
                            AppLogger.p("관전액티비티 시작")
                        } else {
                            makeToast("게임중이 아닙니다.")
                        }

                    }

                }


            }

        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_info -> {
                AppLogger.p("정보버튼")
                true
            }
            R.id.item_key -> {
                AppLogger.p("키버튼")
                setKeyDialog()
                true // 드로어 레이아웃 추가
            }
            R.id.item_delete -> {
                AppLogger.p("삭제버튼")
                viewModel.deleteProfile()
                binding.dlMain.closeDrawer(Gravity.RIGHT)
                true // 드로어 레이아웃 추가
            }
            else -> {
                AppLogger.p("else")
                true
            }
        }
    }

    /**
     * 키셋팅 다이얼로그 생성
     */
    @SuppressLint("SetTextI18n")
    fun setKeyDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_settingkey)
        val params = dialog.window?.attributes
        params?.width = WindowManager.LayoutParams.WRAP_CONTENT
        params?.height = WindowManager.LayoutParams.WRAP_CONTENT

        AppLogger.p("api키?${viewModel.apiKey.value}")
        dialog.tv_key.text = viewModel.apiKey.value

        dialog.show()
        dialog.tv_key.setOnClickListener {
            if (dialog.tv_key.text.isNotEmpty()) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("키 삭제").setMessage("정말로 삭제하시겠습니까?")
                builder.setPositiveButton("삭제") { dialog1, _ ->
//                    viewModel.delApikey()
                    dialog1.dismiss()
                    dialog.tv_key.text = ""
                }
                builder.setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()

                }
                val alertDialog = builder.create()
                alertDialog.show()
            }

        }
        dialog.btn_confirm.setOnClickListener {
            val key = dialog.et_key.text.toString().replace(" ", "")
            if (key.isNotEmpty()) {
                viewModel.setApikey(key)
                dialog.dismiss()
            } else {
                makeToast("키를 입력해주세요.")
            }
        }
        dialog.btn_cancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.btn_newKey.setOnClickListener {
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse("https://developer.riotgames.com/")
            startActivity(i)
        }

    }


    private fun deleteKeyDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("키 삭제").setMessage("정말로 삭제하시겠습니까?")
        builder.setPositiveButton("삭제") { dialog, _ ->
            dialog.dismiss()
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()

        }
        val alertDialog = builder.create()
        alertDialog.show()
    }


}