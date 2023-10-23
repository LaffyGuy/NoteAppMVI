package com.example.noteappmvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.noteappmvi.contract.HasCustomTitle
import com.example.noteappmvi.databinding.ActivityMainBinding
import com.example.noteappmvi.db.Note
import com.example.noteappmvi.fragments.AddNoteBottomSheetDialogFragment
import com.example.noteappmvi.fragments.DetailsFragment
import com.example.noteappmvi.fragments.FavoriteFragment
import com.example.noteappmvi.fragments.NoteFragment
import com.example.noteappmvi.navigation.Navigator

class MainActivity : AppCompatActivity(), Navigator {
    lateinit var bindingClass: ActivityMainBinding

    private val currentFragment: Fragment get() = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)!!

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks(){
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateToolBar()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        supportFragmentManager.beginTransaction().add(R.id.fragmentContainerView, NoteFragment()).commit()

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)


        bindingClass.bottomNavigationView.setOnNavigationItemSelectedListener {
           when(it.itemId){
                R.id.noteFragment -> {
                    openNoteFragment()
                }
                R.id.favoriteFragment -> {
                    openFavoriteFragment()
                }
           }

            true
        }


    }

    override fun openNoteFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, NoteFragment()).commit()
    }

    override fun openNoteBottomSheetDialogFragment() {
        AddNoteBottomSheetDialogFragment().show(supportFragmentManager, "bottomSheetDialog")
    }

    override fun openDetailsFragment(note: Note) {
        supportFragmentManager.beginTransaction().addToBackStack(null).replace(R.id.fragmentContainerView, DetailsFragment.newInstance(note)).commit()
    }

    override fun openFavoriteFragment() {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, FavoriteFragment()).commit()
    }

    override fun goBack() {
        onBackPressed()
    }

    override fun <T : Parcelable> publishResult(result: T) {
        supportFragmentManager.setFragmentResult(result.javaClass.name, bundleOf("hello" to result))
    }

    override fun <T : Parcelable> listenResult(clazz: Class<T>, owner: LifecycleOwner, listener: (T) -> Unit, ) {
        supportFragmentManager.setFragmentResultListener(clazz.name, owner, FragmentResultListener{ key, bundle ->
            listener.invoke(bundle.getParcelable("hello")!!)
        })
    }


    private fun updateToolBar(){

        val fragment = currentFragment

        if(fragment is HasCustomTitle){
            supportActionBar?.title = getString(fragment.getTitleRes())
        }else{
            supportActionBar?.title = getString(R.string.note_app_mvi)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }
}