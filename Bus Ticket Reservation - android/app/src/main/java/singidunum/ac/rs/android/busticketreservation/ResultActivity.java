package singidunum.ac.rs.android.busticketreservation;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import singidunum.ac.rs.android.busticketreservation.data.RouteDto;

public class ResultActivity extends AppCompatActivity {

    public static String RESERVATION_KEY = "reservation_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Parcelable[] parcelableArray = getIntent().getParcelableArrayExtra(SearchActivity.KEY);
        RouteDto[] routes = new RouteDto[parcelableArray.length];
        for(int i = 0; i < parcelableArray.length; i++) {
            routes[i] = (RouteDto) parcelableArray[i];
        }

       ((TextView) findViewById(R.id.resultTextView)).setText("Matches found: " + routes.length);

        ListAdapter adapter = new CustomAdapter(this, routes);
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(ResultActivity.this, ReservationActivity.class);
                RouteDto routeDto = (RouteDto) listView.getItemAtPosition(position);

                Bundle bundle = new Bundle();
                bundle.putParcelable(RESERVATION_KEY, routeDto);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
