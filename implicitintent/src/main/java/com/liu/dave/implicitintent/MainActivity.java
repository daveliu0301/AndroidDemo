package com.liu.dave.implicitintent;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView phoneCallView, mapView, webpagelView, sendEmailView, createCalendarEventView, sendMsgView, getContactView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneCallView = (TextView) findViewById(R.id.phone_call);
        mapView = (TextView) findViewById(R.id.map_view);
        webpagelView = (TextView) findViewById(R.id.web_view);
        sendEmailView = (TextView) findViewById(R.id.send_email);
        createCalendarEventView = (TextView) findViewById(R.id.create_calendar_event);
        sendMsgView = (TextView) findViewById(R.id.send_msg_multiple);
        getContactView = (TextView) findViewById(R.id.get_contact);

        phoneCallView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(makePhoneCall());
            }
        });
        mapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMap();
            }
        });
        webpagelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewInternet();
            }
        });

        sendEmailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(sendEmail());
//                sendEmailShowChooser();
            }
        });

        createCalendarEventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCalendarEvent();
            }
        });

        getContactView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
                pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                startActivityForResult(pickContactIntent, 0);
            }
        });

        sendMsgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("smsto://15201623239; 13511038976");
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra("sms_body", "The SMS text");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            Uri contactUri = data.getData();
            // We only need the NUMBER column, because there will be only one row in the result
            String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER};
            // Perform the query on the contact to get the NUMBER column
            // We don't need a selection or sort order (there's only one result for the given URI)
            // CAUTION: The query() method should be called from a separate thread to avoid blocking
            // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
            // Consider using CursorLoader to perform the query.
            Cursor contactCorsor = getContentResolver().query(contactUri, projection, null, null, null);
            contactCorsor.moveToFirst();
            int column = contactCorsor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String number = contactCorsor.getString(column);
            getContactView.append(number);
        }
    }

    private Intent makePhoneCall() {
        Uri phoneNum = Uri.parse("tel:15201623239");
        return new Intent(Intent.ACTION_DIAL, phoneNum);
    }

    private void viewMap() {
        //TODO error
        // Map point based on address
        Uri location = Uri.parse("geo:0,0?q=1600+Amphitheatre+Parkway,+Mountain+View,+California");
// Or map point based on latitude/longitude
// Uri location = Uri.parse("geo:37.422219,-122.08364?z=14"); // z param is zoom level
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
        startActivity(mapIntent);
    }

    private void viewInternet() {
        Uri webpage = Uri.parse("http://www.bing.com");
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
    }

    private Intent sendEmail() {
        //TODO error
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"767897745@qq.com"});
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "text");
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("/sdcard/cleanType.txt")));
        // You can also attach multiple items by passing an ArrayList of Uris
        return emailIntent;
    }

    //TODO error
    private void sendEmailShowChooser() {
        Intent emailIntent = sendEmail();
        Intent chooser = Intent.createChooser(emailIntent, "choose app");

        // Verify the intent will resolve to at least one activity
        ComponentName componentName = emailIntent.resolveActivity(getPackageManager());
        if (componentName != null) {
            startActivity(chooser);
        }

    }

    private void createCalendarEvent() {
        Intent calendarIntent = new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar calendar = Calendar.getInstance();
        calendar.set(2012, 0, 19, 7, 30);
        long beginTime = calendar.getTimeInMillis();
        calendar.set(2012, 0, 19, 10, 30);
        long endTime = calendar.getTimeInMillis();
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime);
        calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
        calendarIntent.putExtra(CalendarContract.Events.TITLE, "Ninja class");
        calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, "Secret dojo");
        startActivity(calendarIntent);
    }

    private int getHandleableActivityCount(Intent intent) {
        return getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size();
    }

}
