
package com.tiangong.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.tiangong.R;
import com.tiangong.bean.TGUserCenter;
import com.tiangong.db.TGDBUtils;

import java.io.File;

public class InPutDialog extends Dialog implements OnClickListener, TextWatcher {

    private Context context;
    private TextView dialog_title;
    private int title;
    private Button cancel_btn, confirm_btn;
    private int style;
    private EditText dialog_input;
    // private Refresh refresh;

    public InPutDialog(Context context, int theme, int titleid, int style) {
        super(context, theme);
        this.context = context;
        this.title = titleid;
        this.style = style;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_input_dialog);
        initView();
        setListener();
    }

    private void initView() {
        dialog_title = (TextView)findViewById(R.id.dialog_title);
        dialog_title.setText(context.getString(title));
        cancel_btn = (Button)findViewById(R.id.cancel_btn);
        confirm_btn = (Button)findViewById(R.id.confirm_btn);
        dialog_input = (EditText)findViewById(R.id.dialog_input);

        if (style == 1) {
            dialog_input.setText(TGUserCenter.getDefaultUserCenter(getContext()).getIp());
        } else if (style == 2){
            dialog_input.setText(TGUserCenter.getDefaultUserCenter(getContext()).getDdns());
        } else {
            dialog_input.setText(TGUserCenter.getDefaultUserCenter(getContext()).getServer());
            confirm_btn.setText(R.string.update);
        }

    }

    private void setListener() {
        cancel_btn.setOnClickListener(this);
        confirm_btn.setOnClickListener(this);
    }
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                dismiss();
                
                break;
            case R.id.confirm_btn:
                String result = dialog_input.getText().toString();
                if (style == 1) {
                    TGUserCenter.getDefaultUserCenter(getContext()).setIp(result);
                    dismiss();
                } else if (style == 2) {
                    TGUserCenter.getDefaultUserCenter(getContext()).setDdns(result);
                    dismiss();
                } else {
                    TGUserCenter.getDefaultUserCenter(getContext()).setServer(result);
                    HttpUtils down = new HttpUtils();
                    String uri = "http://" + result + ":8080/tiangong/tgzn.db";
                    down.download(uri, TGDBUtils.dirPathd, false, true, new RequestCallBack<File>() {
                        
                        private ProgressDialog mpDialog;

                        @Override
                        public void onSuccess(ResponseInfo<File> arg0) {
                            TGDBUtils.getDB();
                            mpDialog.dismiss();
                            AlertDialog dialog = new AlertDialog.Builder(context).create();
                            dialog.setMessage(context.getResources().getString(R.string.update_success));
                            dialog.setButton(context.getResources().getString(R.string.sure), new OnClickListener() {
                                
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                        
                        @Override
                        public void onFailure(HttpException arg0, String arg1) {
                            mpDialog.dismiss();
                            AlertDialog dialog = new AlertDialog.Builder(context).create();
                            dialog.setMessage(context.getResources().getString(R.string.update_error));
                            dialog.setButton(context.getResources().getString(R.string.sure), new OnClickListener() {
                                
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // TODO Auto-generated method stub
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                        
                        @Override
                        public void onStart() {
                            super.onStart();
                            Log.e("bgxiao","onStart");
                            File dbfile = new File(TGDBUtils.dirPathd);
                            if (dbfile.exists()) {
                                Log.e("bgxiao","db has exist, has delete");
                                dbfile.delete();
                            }
                            mpDialog = new ProgressDialog(context);
                            mpDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条  
                            mpDialog.setIndeterminate(false);//设置进度条是否为不明确  
                            mpDialog.setCancelable(true);//设置进度条是否可以按退回键取消  
       
                            mpDialog.show();  
                        }
                        
                        
                    });
                }
                break;

            default:
                break;
        }
    }

}
