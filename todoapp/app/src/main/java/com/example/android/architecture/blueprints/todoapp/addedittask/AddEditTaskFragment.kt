/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.architecture.blueprints.todoapp.addedittask

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.architecture.blueprints.todoapp.R
import com.google.common.base.Preconditions.checkNotNull
import kotlinx.android.synthetic.main.addtask_frag.add_task_description as mDescription
import kotlinx.android.synthetic.main.addtask_frag.add_task_title as mTitle

/**
 * Main UI for the add task screen. Users can enter a task title and description.
 */
class AddEditTaskFragment constructor() : Fragment(), AddEditTaskContract.View {

    private lateinit var mPresenter: AddEditTaskContract.Presenter

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun setPresenter(presenter: AddEditTaskContract.Presenter) {
        mPresenter = checkNotNull(presenter)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)

        val fab = activity.findViewById(R.id.fab_edit_task_done) as FloatingActionButton
        fab.setImageResource(R.drawable.ic_done)
        fab.setOnClickListener { mPresenter.saveTask(mTitle.text.toString(), mDescription.text.toString()) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(R.layout.addtask_frag, container, false)

    override fun showEmptyTaskError() {
        Snackbar.make(mTitle, getString(R.string.empty_task_message), Snackbar.LENGTH_LONG).show()
    }

    override fun showTasksList() {
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }

    override fun setTitle(title: String?) {
        mTitle.setText(title)
    }

    override fun setDescription(description: String?) {
        mDescription.setText(description)
    }

    override fun isActive(): Boolean = isAdded

    companion object {

        val ARGUMENT_EDIT_TASK_ID = "EDIT_TASK_ID"

        fun newInstance() = AddEditTaskFragment()
    }
}
