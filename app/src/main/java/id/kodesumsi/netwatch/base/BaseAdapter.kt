package id.kodesumsi.netwatch.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseAdapter<VB: ViewBinding, T>(
    private val items: List<T>,
    private val setUpViewBinding: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val bindItemIntoLayout: (item: T, binding: VB) -> Unit
): RecyclerView.Adapter<BaseAdapter.ViewHolder<VB, T>>() {

    class ViewHolder<VB: ViewBinding, T>(private val binding: ViewBinding): RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: T, bindItemIntoLayout: (item: T, binding: VB) -> Unit) {
            @Suppress("UNCHECKED_CAST")
            bindItemIntoLayout(item, binding as VB)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<VB, T> {
        return ViewHolder(setUpViewBinding.invoke(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder<VB, T>, position: Int) {
        holder.bindItem(items[position], bindItemIntoLayout)
    }

    override fun getItemCount(): Int = items.size

}