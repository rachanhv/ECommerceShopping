package vinformax.vinmart.snackbar;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by subbian.goutham on 12/10/2015.
 */
public class SnackBar extends AppCompatActivity {

    public SnackBar(View v, String comments){

        Snackbar.make(v, comments, Snackbar.LENGTH_LONG).show();

    }



}
