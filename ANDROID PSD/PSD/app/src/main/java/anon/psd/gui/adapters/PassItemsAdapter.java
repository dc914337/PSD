package anon.psd.gui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import anon.psd.R;
import anon.psd.models.AppearancesList;
import anon.psd.models.PassItem;
import anon.psd.models.gui.PrettyPassword;

/**
 * Created by Dmitry on 04.07.2015.
 */
public class PassItemsAdapter<T extends PrettyPassword> extends ArrayAdapter<T>
{
    private static class ViewHolder
    {
        TextView passTitle;
        TextView passLogin;
        TextView passUsedTimes;
        ImageView passImg;
    }

    public PassItemsAdapter(Context context, int resource, AppearancesList objects)
    {
        super(context, R.layout.lv_pass_item, (java.util.List<T>) objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        PrettyPassword wrappedPass = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.lv_pass_item, parent, false);
            viewHolder.passTitle = (TextView) convertView.findViewById(R.id.passTitle);
            viewHolder.passLogin = (TextView) convertView.findViewById(R.id.passLogin);
            viewHolder.passUsedTimes = (TextView) convertView.findViewById(R.id.passUsed);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PassItem passItem = wrappedPass.getPassItem();
        viewHolder.passTitle.setText(String.format("%s", passItem.Title));
        viewHolder.passLogin.setText(String.format("Login: %s %s", passItem.Login, passItem.EnterWithLogin ? "(will be entered)" : ""));//they don't have empty string.
        viewHolder.passUsedTimes.setText(String.format("Used %s times", wrappedPass.UsedDates.size()));

        return convertView;
    }
}
