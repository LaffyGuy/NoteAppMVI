package com.example.noteappmvi.navigation

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.noteappmvi.db.Note


fun Fragment.navigate(): Navigator{
    return requireActivity() as Navigator
}


interface Navigator {

    fun openNoteFragment()

    fun openNoteBottomSheetDialogFragment()

    fun openDetailsFragment(note: Note)

    fun openFavoriteFragment()

    fun goBack()

    fun <T : Parcelable> publishResult(result: T)

    fun <T : Parcelable> listenResult(clazz: Class<T>, owner: LifecycleOwner, listener: (T) -> Unit)

}