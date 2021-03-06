/*
 * This file is part of Material Audiobook Player.
 *
 * Material Audiobook Player is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * Material Audiobook Player is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Material Audiobook Player. If not, see <http://www.gnu.org/licenses/>.
 * /licenses/>.
 */

package de.ph1b.audiobook.features.folder_chooser

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import de.ph1b.audiobook.R
import de.ph1b.audiobook.misc.drawable
import de.ph1b.audiobook.misc.layoutInflater
import kotlinx.android.synthetic.main.activity_folder_chooser_adapter_row_layout.view.*
import java.io.File
import java.util.*


/**
 * Adapter for displaying files and folders.
 * Constructor that initializes the class with the necessary values
 *
 * @param c    The context
 * @param mode The operation mode which defines the interaction.
 *
 * @author Paul Woitaschek
 */
class FolderChooserAdapter(private val c: Context,
                           private val mode: FolderChooserActivity.OperationMode,
                           private val listener: (selected: File) -> Unit)
: RecyclerView.Adapter<FolderChooserAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind(data[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = parent.layoutInflater().inflate(R.layout.activity_folder_chooser_adapter_row_layout, parent, false)
        return Holder(view)
    }

    override fun getItemCount() = data.size

    private val data = ArrayList<File>()

    fun newData(newData: List<File>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    inner class Holder(private val root: View) : RecyclerView.ViewHolder(root) {

        init {
            root.setOnClickListener {
                listener.invoke(data[adapterPosition])
            }
        }

        fun bind(selectedFile: File) {
            val isDirectory = selectedFile.isDirectory

            root.text.text = selectedFile.name

            // if its not a collection its also fine to pick a file
            if (mode == FolderChooserActivity.OperationMode.COLLECTION_BOOK) {
                root.text.isEnabled = isDirectory
            }

            val icon = c.drawable(if (isDirectory) R.drawable.ic_folder else R.drawable.ic_album)
            root.icon.setImageDrawable(icon)
            root.icon.contentDescription =
                    c.getString(if (isDirectory) R.string.content_is_folder else R.string.content_is_file)
        }
    }
}
