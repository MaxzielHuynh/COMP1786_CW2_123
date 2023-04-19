package vn.edu.greenwich.cw_2_sample_123;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected static final String _FILE_NAME = "cw2_file_image.txt";

    protected ImageView imageView;
    protected EditText eImageLink;
    protected Button btnPreceding, btnFollowing, btnInsert;

    protected ArrayList<String> _imageList;
    protected int _currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.imageView);
        eImageLink = findViewById(R.id.eImageLink);
        btnPreceding = findViewById(R.id.btnPreceding);
        btnFollowing = findViewById(R.id.btnFollowing);
        btnInsert = findViewById(R.id.btnInsert);

        btnInsert.setOnClickListener(v -> insertImage());
        btnFollowing.setOnClickListener(v -> followingImage());
        btnPreceding.setOnClickListener(v -> precedingImage());

        _imageList = getImageList();
        _currentIndex = 0;

        loadImage();
    }

    // Ex.01.
    protected ArrayList<String> getImageList() {
        ArrayList<String> imageList = new ArrayList<>();

        imageList.add("https://onlinejpgtools.com/images/examples-onlinejpgtools/mountains-near-water-better-quality.jpg");
        imageList.add("https://images.pexels.com/photos/460672/pexels-photo-460672.jpeg");

        getImageListFromFile(imageList);

        Toast.makeText(this, "Retrieve the list without errors.", Toast.LENGTH_SHORT).show();

        return imageList;
    }

    // Ex.01.
    protected void loadImage() {
        Picasso.with(this).load(_imageList.get(_currentIndex)).into(imageView);
    }

    // Ex.02.
    protected void insertImage() {
        String imageURL = eImageLink.getText().toString();

        _imageList.add(imageURL);
        writeURLToFile(imageURL);

        eImageLink.setText("");

        Toast.makeText(this, "Successfully inserted.", Toast.LENGTH_SHORT).show();
    }

    // Ex.01.
    protected void followingImage() {
        ++_currentIndex;
        loadImage();
    }

    // Ex.01.
    protected void precedingImage() {
        --_currentIndex;
        loadImage();
    }

    // Ex.03.
    protected void writeURLToFile(String url) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput(_FILE_NAME, MODE_APPEND));
            outputStreamWriter.write(url);
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "No file matching the criteria.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ex.03.
    private void getImageListFromFile(ArrayList<String> imageList) {
        try {
            InputStream inputStream = openFileInput(_FILE_NAME);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String url;
                while ((url = bufferedReader.readLine()) != null) {
                    imageList.add(url);
                }

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Not located the file.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}