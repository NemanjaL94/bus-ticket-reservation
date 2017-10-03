package singidunum.ac.rs.android.busticketreservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import singidunum.ac.rs.android.busticketreservation.data.RouteDto;


public class CustomAdapter extends ArrayAdapter<RouteDto> {

    CustomAdapter(Context context, RouteDto[] routes) {
        super(context, R.layout.custom_row, routes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row, parent, false);

        RouteDto route = getItem(position);

        TextView busCompanyTextView = (TextView) customView.findViewById(R.id.busCompanyTextVIew);
        TextView scheduleTextView = (TextView) customView.findViewById(R.id.scheduleTextView);
        TextView cityTextView = (TextView) customView.findViewById(R.id.cityTextView);

        busCompanyTextView.setText(route.getBusCompanyName());
        scheduleTextView.setText(route.getSchedule());
        cityTextView.setText(route.getRoute());

        return customView;
    }

}
