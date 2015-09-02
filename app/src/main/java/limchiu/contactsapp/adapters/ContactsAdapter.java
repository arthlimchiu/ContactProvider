package limchiu.contactsapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import limchiu.contactsapp.R;

/**
 * Created by Clarence on 9/2/2015.
 */
public class ContactsAdapter extends CursorAdapter {

    String[] projection = new String[]{
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    String selection = ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?";

    public ContactsAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_contact, viewGroup, false);

        ViewHolder holder = new ViewHolder();
        holder.mImage = (ImageView) v.findViewById(R.id.item_contact_image);
        holder.mName = (TextView) v.findViewById(R.id.item_contact_name);
        holder.mPhone = (TextView) v.findViewById(R.id.item_contact_number);

        v.setTag(holder);

        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();

        String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
        String image = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI));
        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

        if (TextUtils.isEmpty(image)) {
            holder.mImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(context).load(Uri.parse(image)).centerCrop().into(holder.mImage);
        }

        holder.mName.setText(name);

        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projection, selection, new String[]{id}, null);
        while (c.moveToNext()) {
            holder.mPhone.setText(c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }
        c.close();

    }

    class ViewHolder {
        ImageView mImage;
        TextView mName;
        TextView mPhone;
    }
}
