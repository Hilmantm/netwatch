package id.kodesumsi.netwatch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BaseBottomSheet<VB: ViewBinding>(
    private val setupViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val setupViewInstaceParam: (binding: VB, savedInstanceState: Bundle?, baseBottomSheet: BaseBottomSheet<VB>) -> Unit
): BottomSheetDialogFragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setupViewBinding.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewInstaceParam(binding, savedInstanceState, this)
    }

}