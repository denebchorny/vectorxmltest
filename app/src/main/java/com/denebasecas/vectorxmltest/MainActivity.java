package com.denebasecas.vectorxmltest;

import android.os.Environment;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.fromDcmi)
    ImageView mFromDmci;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Validating the file exists
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/cityscape.xml");
        if(file.exists()){
            Toast.makeText(getApplicationContext(), "Exists file", Toast.LENGTH_LONG).show();
        }

        XmlPullParser drawable = null;
        try {
            drawable = xmlVectorFromResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (drawable != null) {
            try {
                mFromDmci.setImageDrawable(VectorDrawableCompat.createFromXml(getResources() , drawable));
            } catch (XmlPullParserException | IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public XmlPullParser xmlVectorFromResource() throws FileNotFoundException, IOException {
        try {
            String data;
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            /*factory.setValidating(true);*/
            factory.setNamespaceAware(true);
            // testing loading a .xml from DCMI
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/cityscape.xml");
            FileInputStream fis = new FileInputStream(file.getPath());
            InputStreamReader isr = new InputStreamReader(fis);

            char[] InputBuffer = new char[fis.available()];
            isr.read(InputBuffer);
            data = new String(InputBuffer);
            isr.close();
            fis.close();
            XmlPullParser xpp = null;
            xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            return xpp;
        } catch (XmlPullParserException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
