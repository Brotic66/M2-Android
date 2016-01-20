package brotic.findmyfriends.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import brotic.findmyfriends.AsyncTask.GetDemandsTask;
import brotic.findmyfriends.Event.FriendClickListener;
import brotic.findmyfriends.Exception.SecurityContextException;
import brotic.findmyfriends.R;
import brotic.findmyfriends.Security.MyActivity;
import brotic.findmyfriends.Service.BroticCommunication;
import brotic.findmyfriends.Service.CircleTransform;

public class AddFriendActivity extends MyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setTitle("");

        this.findViewById(R.id.suivant_add).setOnClickListener(
                new FriendClickListener((EditText) this.findViewById(R.id.addPseudo)));

        ContentResolver cr = this.getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        LayoutInflater inflater = (LayoutInflater) MyActivity.getAct().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout parent = (LinearLayout) MyActivity.getAct().findViewById(R.id.listeView);
        if (cur.getCount() > 0)
        {
            while (cur.moveToNext())
            {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0)
                {
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?", new String[]{id}, null);
                    while (pCur.moveToNext())
                    {
                        String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        RelativeLayout custom = (RelativeLayout) inflater.inflate(R.layout.element_contact, null);

                        TextView username = (TextView) custom.findViewById(R.id.pseudo);
                        username.setText(name);
                        TextView phoneNumer = (TextView) custom.findViewById(R.id.phoneNbr);
                        phoneNumer.setText(phoneNo);

                        custom.findViewById(R.id.buttonSms).setOnClickListener(new FriendClickListener(phoneNo));

                        parent.addView(custom);
                    }
                    pCur.close();
                }
            }
            cur.close();
        }
    }
}
