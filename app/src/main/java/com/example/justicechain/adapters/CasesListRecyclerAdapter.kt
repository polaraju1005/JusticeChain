package com.example.justicechain.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.justicechain.R
import com.example.justicechain.SelectedCaseActivity
import com.example.justicechain.models.CaseDetailsData
import com.example.justicechain.models.LawyerViewCasesActivity

class CasesListRecyclerAdapter(
    val context: LawyerViewCasesActivity,
    var casesList: ArrayList<CaseDetailsData>
) : RecyclerView.Adapter<CasesListRecyclerAdapter.HomeViewHolder>() {
    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var caseNum = view.findViewById<TextView>(R.id.txtCaseNum)
        val stack = view.findViewById<ConstraintLayout>(R.id.cases_recycler_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.row_cases, parent, false)
        return HomeViewHolder(view)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentUser: CaseDetailsData = casesList[position]

        holder.caseNum.text = currentUser.getClientName().toString()

        holder.stack.setOnClickListener {
            val i = Intent(context, SelectedCaseActivity::class.java)

            i.putExtra("caseType", currentUser.getCaseType())
            i.putExtra("judgeName",currentUser.getJudgeName())
            i.putExtra("lawyerName",currentUser.getLawyerName())
            i.putExtra("clientName",currentUser.getClientName())
            i.putExtra("caseNum",currentUser.getCaseNum())
            i.putExtra("fileNum",currentUser.getFileNum())
            i.putExtra("fileDate",currentUser.getFileDate())
            i.putExtra("phone",currentUser.getPhone())
            i.putExtra("desc",currentUser.getDesc())

            context.startActivity(i)
        }

    }

    override fun getItemCount(): Int {
        return casesList.size
    }
}