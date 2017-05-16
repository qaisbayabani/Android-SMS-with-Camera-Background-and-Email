package okokok.smscam;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/** Called when the activity is first created. */

public class DrawActivity extends Activity {
        // Our variables

CameraPreview cv;

CanvasView dv;

Drvi drv1;




FrameLayout framelayout1;


LinearLayout linearlayout1;


GridLayout gridlayout2;




    AutoCompleteTextView et1;

    private ArrayList<Map<String, String>> arrayListMap;

    private SimpleAdapter simpleAdapter;

    String name,number,value,value1;

    EditText e1;

    Button b1;

    Button b3;



    @Override
        public void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);


            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


         }




        public void Load(){

            // Try to get the camera
            Camera c = getCameraInstance();

            // If the camera was received, create the app
            if (c != null){
                // Create our layout in order to layer the
                // draw view on top of the camera preview.


                framelayout1 = new FrameLayout(this);

                linearlayout1=new LinearLayout(this);


                gridlayout2=new GridLayout(this);

                gridlayout2.setColumnCount(4);

                gridlayout2.setRowCount(4);






                framelayout1.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT));

                linearlayout1.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT));

                gridlayout2.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT));






                et1 = new AutoCompleteTextView(this);


                et1.setHint("Enter name or phone");
                et1.setEms(15);
                et1.setTextColor(Color.RED);
                et1.setHintTextColor(Color.RED);
                et1.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT));


                arrayListMap = new ArrayList<Map<String, String>>();

                PopulatePeopleList();

                simpleAdapter = new SimpleAdapter(this, arrayListMap, R.layout.custcontview,

                        new String[] { "Name", "Phone", "Type" }, new int[] {
                        R.id.ccontName, R.id.ccontNo, R.id.ccontType });
                et1.setAdapter(simpleAdapter);
                et1.setOnItemClickListener(new AdapterView.OnItemClickListener() {


                    @Override


                    public void onItemClick(AdapterView<?> av, View arg1, int index,
                                            long arg3) {
                        Map<String, String> map = (Map<String, String>) av.getItemAtPosition(index);

                        name = map.get("Name");
                        number = map.get("Phone");
                        //autoCompleteTextView.setText(""+name+"<"+number+">");
                        et1.setText("" + number + "");
                    }
                });


                b1=new Button(this);

                Button b2=new Button(this);

                b1.setText("Send");
                b1.setWidth(23);

                b1.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT));

                // Create a new camera view and add it to the layout


                cv = new CameraPreview(this,c);

               // dv = new CanvasView(this);

                drv1=new Drvi(this);

                ScrollView scrollView = new ScrollView(this);

               // HorizontalScrollView hsv = new HorizontalScrollView(this);
               // hsv.addView(scrollView);




              //framelayout1.addView(dv);


                e1 = new EditText(this);

                EditText e2=new EditText(this);


                e1.setTextColor(Color.RED);
                e1.setHint("Type Your Message...");
                e1.setHintTextColor(Color.RED);
                e1.setEms(15);
                e1.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT));


                e2.setTextColor(Color.RED);
                e2.setText("Phone");
                e2.setEms(15);
                e2.setLayoutParams(new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.FILL_PARENT,
                        ViewGroup.LayoutParams.FILL_PARENT));

                // Create a new draw view and add it to the layout


                scrollView.addView(drv1);

                //scrollView.scrollTo(700,10);


                GridLayout.Spec row = GridLayout.spec(0 , 1);
                GridLayout.Spec colspan = GridLayout.spec(0 , 1);
                GridLayout.LayoutParams gridLayoutParam = new GridLayout.LayoutParams(row , colspan);
                gridlayout2.addView(b1,gridLayoutParam);

                row = GridLayout.spec(0 , 1);
                colspan = GridLayout.spec(1 , 1);
                gridLayoutParam = new GridLayout.LayoutParams(row , colspan);
                gridlayout2.addView(e1,gridLayoutParam);


                row = GridLayout.spec(1 , 1);
                colspan = GridLayout.spec(1 , 2);
                gridLayoutParam = new GridLayout.LayoutParams(row , colspan);
                gridlayout2.addView(et1, gridLayoutParam);

                b3=new Button(this);
                b3.setText("History");
                row = GridLayout.spec(2 , 1);
                colspan = GridLayout.spec(0 , 1);
                gridLayoutParam = new GridLayout.LayoutParams(row , colspan);
                gridlayout2.addView(b3,gridLayoutParam);


		framelayout1.addView(cv);

                framelayout1.addView(scrollView);

	        linearlayout1.addView(framelayout1);

                setContentView(linearlayout1);

             //   LayoutInflater inflater = getLayoutInflater();

                LayoutInflater inflater = getLayoutInflater();

                getWindow().addContentView(gridlayout2,

                        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));

            }
            // If the camera was not received, close the app
            else {
                Toast toast = Toast.makeText(getApplicationContext(),"Unable to find camera. Closing", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }



            b1.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {


                    value = e1.getText().toString();

                    value1 = et1.getText().toString();



                    sendSMS(value1, value);

                    //dv.getstr(name, number);

                    //dv.invalidate();




                    try {

                     //   showDialog(name,number,value);
                        //File sdcard = Environment.getExternalStorageDirectory();
//Get the text file
                        File f1 = new File("/sdcard/smslo/"+value1);
//Read text from file
                        StringBuilder t5 = new StringBuilder();

                        try {
                            BufferedReader br = new BufferedReader(new FileReader(f1));
                            String line;

                            while ((line = br.readLine()) != null) {

                                t5.append(line);
                                t5.append("\n");
                            }
                            br.close();
                        } catch (IOException e) {
                            //You'll need to add proper error handling here
                        }


                        File myFile = new File("/sdcard/smslo/" + value1);

                        FileOutputStream fOut = new FileOutputStream(myFile);

                        OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);


                        if (myFile.exists()) {


                            t5.append("\n");

                            t5.append("You Say...");

                            t5.append(value);

                            t5.append("  ");

                            myOutWriter.append(t5);

                            myOutWriter.close();

                            fOut.close();

                            e1.setText("");


                        } else {


                            myFile.createNewFile();


                            myOutWriter.append(number);

                            myOutWriter.append("msg:");

                            myOutWriter.append(value);


                            myOutWriter.close();
                            fOut.close();
                            e1.setText("");

                        }


                    } catch (Exception e) {
                        Log.e("ERRR", "Could not create file", e);
                    }


                }
            });//end of butcli

            b3.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
/*
                    Intent intent= new Intent("com.example.khan.samp.DrawActII");

                    intent.putExtra("nam", et1.getText().toString());

                    startActivity(intent);
*/

                    Intent intent = new Intent("android.intent.action.DIAL",
                            Uri.parse(et1.toString()));
                }
            });


        }

        // This method is strait for the Android API
        // A safe way to get an instance of the Camera object.
        public static Camera getCameraInstance(){
            Camera c = null;

            try {
                c = Camera.open();// attempt to get a Camera instance
            }
            catch (Exception e){
                // Camera is not available (in use or does not exist)
                e.printStackTrace();
            }
            return c; // returns null if camera is unavailable
        }

        // Override the onPause method so that we
        // can release the camera when the app is closing.

        @Override
        protected void onPause() {
            super.onPause();

            if (cv != null){
                cv.onPause();
                cv = null;
            }
        }

        // We call Load in our Resume method, because
        // the app will close if we call it in onCreate
        @Override
        protected void onResume(){
            super.onResume();
            Load();
        }


    public void PopulatePeopleList() {


        arrayListMap.clear();

        Cursor people = getContentResolver().query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        while (people.moveToNext()) {
            String contactName = people.getString(people
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String contactId = people.getString(people
                    .getColumnIndex(ContactsContract.Contacts._ID));
            String hasPhone = people
                    .getString(people
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

            if ((Integer.parseInt(hasPhone) > 0)){
                // You know have the number so now query it like this
                Cursor phones = getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ contactId,
                        null, null);
                while (phones.moveToNext()){
                    //store numbers and display a dialog letting the user select which.
                    String phoneNumber = phones.getString(
                            phones.getColumnIndex(
                                    ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String numberType = phones.getString(phones.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.TYPE));
                    Map<String, String> NamePhoneType = new HashMap<String, String>();
                    NamePhoneType.put("Name", contactName);
                    NamePhoneType.put("Phone", phoneNumber);
                    if(numberType.equals("0"))
                        NamePhoneType.put("Type", "Work");
                    else
                    if(numberType.equals("1"))
                        NamePhoneType.put("Type", "Home");
                    else if(numberType.equals("2"))
                        NamePhoneType.put("Type",  "Mobile");
                    else
                        NamePhoneType.put("Type", "Other");
                    //Then add this map to the list.
                    arrayListMap.add(NamePhoneType);
                }
                phones.close();
            }
        }
        people.close();
        startManagingCursor(people);
    }

    private void sendSMS(String phoneNumber, String message) {

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);

    }


    public void showDialog(String n3, String p3, String msg3) throws Exception
    {

        EditText e5= new EditText(this);
        EditText e6= new EditText(this);


//Canvas c5=new Canvas(DrawActivity.this);

        AlertDialog.Builder builder = new AlertDialog.Builder(DrawActivity.this);

        builder.setTitle(""+n3+"<"+p3+">");
        //builder.setMessage(""+name+"<"+phone+">");

//        builder.setMessage("Enter Password");

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);


        e5.setLayoutParams(lp);

        e6.setLayoutParams(lp);

        e6.setText(msg3);

        builder.setView(e5);
        builder.setView(e6);


        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {

            @Override

            public void onClick(DialogInterface dialog, int which) {



            }
        });

        builder.setNegativeButton("Exit", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });


        builder.show();
    }



    }








