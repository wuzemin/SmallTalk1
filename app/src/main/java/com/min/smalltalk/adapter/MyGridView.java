package com.min.smalltalk.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.min.mylibrary.widget.image.SelectableRoundedImageView;
import com.min.smalltalk.App;
import com.min.smalltalk.R;
import com.min.smalltalk.activity.SelectFriendsActivity;
import com.min.smalltalk.activity.UserDetailActivity;
import com.min.smalltalk.bean.Friend;
import com.min.smalltalk.bean.GroupMember;
import com.min.smalltalk.wedget.CharacterParser;
import com.min.smalltalk.wedget.Generate;

import java.util.ArrayList;
import java.util.List;

import io.rong.imageloader.core.ImageLoader;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;

/**
 * Created by Min on 2016/12/1.
 */

public class MyGridView extends BaseAdapter {
    private Context context;
    private List<GroupMember> list=new ArrayList<>();
    private String isCreator;
    private LayoutInflater inflater;
    private String groupId;
    private String groupName;
    private String groupPortraitUri;

    public MyGridView(Context context, List<GroupMember> list,String isCreator) {
        this.context = context;
        if (list.size() >= 20) {
            this.list = list.subList(0, 19);
        } else {
            this.list = list;
        }
        this.isCreator=isCreator;
        this.inflater=inflater.from(context);
    }

    @Override
    public int getCount() {
        if (isCreator=="1") {
            return list.size() + 2;
        } else {
            return list.size() + 1;
        }
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_groups_list,null);
            holder=new ViewHolder();
            holder.sivGroupDetails= (SelectableRoundedImageView) convertView.findViewById(R.id.siv_group_details_head);
            holder.tvGroupDetailsName= (TextView) convertView.findViewById(R.id.tv_group_details_name);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
//        groupId=list.get(position).getGroupId();
//        groupName=list.get(position).getGroupName();
//        groupPortraitUri=list.get(position).getGroupPortraitUri();
        /*if(list.get(position).getGroupPortraitUri()!                         =null) {
            ImageLoader.getInstance().displayImage(groupPortraitUri, holder.sivGroupDetails);
        }else {
            holder.sivGroupDetails.setImageResource(R.mipmap.default_fmessage);
        }*/

        //
        if(position==getCount()-1 && isCreator=="1"){
            holder.tvGroupDetailsName.setText("");
            holder.sivGroupDetails.setImageResource(R.mipmap.icon_btn_deleteperson);
            holder.sivGroupDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SelectFriendsActivity.class);
                    intent.putExtra("isDeleteGroupMember", true);
                    intent.putExtra("GroupId", list.get(position).getGroupId());
                    context.startActivity(intent);
                }
            });
        }else if ((isCreator=="1" && position == getCount() - 2) || (isCreator!="1" && position == getCount() - 1)) {
            holder.tvGroupDetailsName.setText("");
            holder.sivGroupDetails.setImageResource(R.mipmap.jy_drltsz_btn_addperson);

            holder.sivGroupDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, SelectFriendsActivity.class);
                    intent.putExtra("isAddGroupMember", true);
                    intent.putExtra("GroupId", list.get(position).getGroupId());
                    context.startActivity(intent);

                }
            });
        } else { // 普通成员
            final GroupMember bean = list.get(position);
            holder.tvGroupDetailsName.setText(list.get(position).getUserName());
            if (TextUtils.isEmpty(bean.getUserPortraitUri())) {
                ImageLoader.getInstance().displayImage(Generate.generateDefaultAvatar(bean.getUserName(), bean.getUserId()), holder.sivGroupDetails, App.getOptions());
            } else {
                ImageLoader.getInstance().displayImage(bean.getUserPortraitUri(), holder.sivGroupDetails, App.getOptions());
            }
            holder.sivGroupDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserInfo userInfo = new UserInfo(bean.getUserId(), bean.getUserName(),
                            Uri.parse(TextUtils.isEmpty(bean.getUserPortraitUri()) ? Generate.generateDefaultAvatar(bean.getUserName(), bean.getUserId()) : bean.getUserPortraitUri()));
                    Intent intent = new Intent(context, UserDetailActivity.class);
                    Friend friend = CharacterParser.getInstance().generateFriendFromUserInfo(userInfo);
                    intent.putExtra("friend", friend);
                    intent.putExtra("conversationType", Conversation.ConversationType.GROUP.getValue());
                    //Groups not Serializable,just need group name
                    intent.putExtra("groupName", list.get(position).getGroupName());
                    intent.putExtra("type", 1);
                    context.startActivity(intent);
                }

            });

        }
        return convertView;
    }

    static class ViewHolder{
        SelectableRoundedImageView sivGroupDetails;
        TextView tvGroupDetailsName;
    }
}
