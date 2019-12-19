package com.example.admin.admin.LockerList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.admin.Model.ListViewItem;
import com.example.admin.admin.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        LinearLayout ListLayout = (LinearLayout) convertView.findViewById(R.id.ListLayout);
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView lnoTextView = (TextView)convertView.findViewById(R.id.text_LNO) ;
        TextView nameTextView = (TextView) convertView.findViewById(R.id.text_Name) ;
        TextView addr1TextView = (TextView) convertView.findViewById(R.id.text_Addr1) ;
        TextView addr2TextView = (TextView) convertView.findViewById(R.id.text_Addr2) ;
        TextView addr3TextView = (TextView) convertView.findViewById(R.id.text_Addr3) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewItem.getIconDrawable());
        lnoTextView.setText(listViewItem.getLnoStr());
        nameTextView.setText(listViewItem.getNameStr());
        addr1TextView.setText(listViewItem.getAddr1Str());
        addr2TextView.setText(listViewItem.getAddr2Str());
        addr3TextView.setText(listViewItem.getAddr3Str());

        if (listViewItem.getAddr3Str().equals("I") || listViewItem.getAddr3Str().equals("O")  ){
            addr3TextView.setVisibility(View.GONE);
        }

        if (listViewItem.getAddr3Str().equals("I")){ // 물건있을시 배경색 회색+ 클릭이벤트 제외
            ListLayout.setBackgroundResource(R.drawable.thinfill_border);

            convertView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return true;
                }});
        } else { // 물건있을시 배경색 흰색 +클릭이벤트 활성화

            convertView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }});
        }



        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String lno, String name, String addr1, String addr2, String addr3) {
        ListViewItem item = new ListViewItem();

        item.setIconDrawable(icon);
        item.setLnoStr(lno);
        item.setNameStr(name);
        item.setAddr1Str(addr1);
        item.setAddr2Str(addr2);
        item.setAddr3Str(addr3);

        listViewItemList.add(item);
    }
}
