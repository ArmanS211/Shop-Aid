package com.example.shopaid;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;




public class CustomAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;
    private boolean checked = false;

    boolean[] checkBoxState;


    ViewHolder viewHolder;


    public CustomAdapter(Context context, String[] values) {
        super(context, R.layout.rowlayout, values);
        this.context = context;
        this.values = values;
        checkBoxState = new boolean[values.length];
    }

    private class ViewHolder
    {
        TextView name;
        CheckBox checkBox;

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public TextView getName() {
            return name;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.rowlayout, null);
            viewHolder=new ViewHolder();

            viewHolder.name= convertView.findViewById(R.id.label);
            viewHolder.checkBox= convertView.findViewById(R.id.checkBox);

            convertView.setTag( viewHolder);


        }
        else
            viewHolder=(ViewHolder) convertView.getTag();

        viewHolder.name.setText(values[position]);



        String s = values[position];

        viewHolder.checkBox.setChecked(checkBoxState[position]);


        viewHolder.checkBox.setOnClickListener(v -> {
            if(((CheckBox)v).isChecked()) {
                checkBoxState[position] = true;
            }
            else
                checkBoxState[position]=false;

        });

        return convertView;
    }

    public boolean[] getCheckBoxState(){
        return checkBoxState;
    }

    public String getName(int pos){
        String val = values[pos];
        return val;
    }
    //Citation: custom adapter code based on sample code provided in course.

}



