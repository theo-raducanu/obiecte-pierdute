package com.example.android.obiectepierdute;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends ArrayAdapter {

    List list= new ArrayList();

    public ReportAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Report object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {

        View row;
        row=convertView;
        ReportHolder reportHolder;
        if(row==null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row= layoutInflater.inflate(R.layout.report_layout,parent,false);
            reportHolder= new ReportHolder();

            reportHolder.tx_nume = (TextView) row.findViewById(R.id.tx_nume);
            reportHolder.tx_tip_obj = (TextView) row.findViewById(R.id.tx_tip_obj);
            /*reportHolder.tx_obiect = (TextView) row.findViewById(R.id.tx_obiect);*/
            reportHolder.tx_descriere = (TextView) row.findViewById(R.id.tx_descriere);
            reportHolder.tx_locatie = (TextView) row.findViewById(R.id.tx_locatie);
            /*reportHolder.tx_email = (TextView) row.findViewById(R.id.tx_email);*/
            /*reportHolder.tx_nr_tel = (TextView) row.findViewById(R.id.tx_email);*/
            row.setTag(reportHolder);
        } else {
            reportHolder= (ReportHolder) row.getTag();
        }

        Report report= (Report) this.getItem(position);
        reportHolder.tx_nume.setText(report.getNume());
        reportHolder.tx_tip_obj.setText(report.getTip());
        /*reportHolder.tx_obiect.setText(report.getObiect());*/
        reportHolder.tx_descriere.setText(report.getDescriere());
        reportHolder.tx_locatie.setText(report.getLocatie());
        /*reportHolder.tx_email.setText(report.getEmail());*/
        /*reportHolder.tx_nr_tel.setText(report.getNrTel());*/
        return row;
    }

    static class ReportHolder{

        TextView tx_nume, tx_tip_obj, tx_obiect, tx_descriere, tx_locatie, tx_email, tx_nr_tel;

    }
}
