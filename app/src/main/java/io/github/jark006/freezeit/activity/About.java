package io.github.jark006.freezeit.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import io.github.jark006.freezeit.R;
import io.github.jark006.freezeit.StaticData;
import io.github.jark006.freezeit.Utils;


public class About extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        findViewById(R.id.coolapk_link).setOnClickListener(this);

        findViewById(R.id.qq_group_link).setOnClickListener(this);
        findViewById(R.id.qq_channel_link).setOnClickListener(this);
        findViewById(R.id.website_link).setOnClickListener(this);
        findViewById(R.id.tutorial_link).setOnClickListener(this);
        findViewById(R.id.changelog_text).setOnClickListener(this);
        findViewById(R.id.privacy_text).setOnClickListener(this);

    }

    @Override
    public void onResume() {
        super.onResume();
        findViewById(R.id.container).setBackground(StaticData.getBackgroundDrawable(this));
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.coolapk_link) {
            try {
                Intent intent = new Intent();
                intent.setClassName("com.coolapk.market", "com.coolapk.market.view.AppLinkActivity");
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse("coolmarket://u/24268987"));
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.coolapk_link))));
            }
        } else if (id == R.id.github_link) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.github_link))));
        } else if (id == R.id.qq_group_link) {
            try {
                //【冻它模块 freezeit】(781222669) 的 key 为： ntLAwm7WxB0hVcetV7DsxfNTVN16cGUD
                String key = "ntLAwm7WxB0hVcetV7DsxfNTVN16cGUD";
                Intent intent = new Intent();
                intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26jump_from%3Dwebapi%26k%3D" + key));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.qq_group_link))));
            }
        } else if (id == R.id.qq_channel_link) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.qq_channel_link))));
        } else if (id == R.id.website_link) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.website_link))));
        } else if (id == R.id.tutorial_link) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.tutorial_link))));
        } else if (id == R.id.changelog_text) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.online_changelog_link))));
        } else if (id == R.id.privacy_text) {
            Utils.textDialog(this, R.string.privacy_title, R.string.privacy_content);
        }
    }
}