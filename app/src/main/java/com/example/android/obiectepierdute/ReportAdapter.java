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
            reportHolder.tx_hartuire = (TextView) row.findViewById(R.id.tx_hartuire);
            reportHolder.tx_mesaj = (TextView) row.findViewById(R.id.tx_mesaj);
            reportHolder.tx_locatie = (TextView) row.findViewById(R.id.tx_locatie);
            row.setTag(reportHolder);
        } else {
            reportHolder= (ReportHolder) row.getTag();
        }

        Report report= (Report) this.getItem(position);
        reportHolder.tx_nume.setText(report.getNume());
        reportHolder.tx_hartuire.setText(report.getHartuire());
        reportHolder.tx_mesaj.setText(report.getMesaj());
        reportHolder.tx_locatie.setText(report.getLocatie());
        return row;
    }

    static class ReportHolder{

        TextView tx_nume, tx_hartuire, tx_mesaj, tx_locatie, tx_email, tx_utilizator, tx_privacy;

    }
}
