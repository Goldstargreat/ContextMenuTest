package kr.ac.kopo.contextmenutest;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity
{
    Button btnRotation, btnZoomin;
    LinearLayout linear;

    float rotationDegree;   // 수정

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btnRotation = findViewById(R.id.btn_bg);
        btnZoomin = findViewById(R.id.btn_change);
        linear = findViewById(R.id.main);
        Button btnAlert = findViewById(R.id.btn_alert);
        btnAlert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("대화상자연습");
                dialog.setMessage("대화상자 내의 내용 부분입니다");
                dialog.setIcon(R.drawable.icon);
                dialog.show();
            }
        });

        registerForContextMenu(btnRotation);
        registerForContextMenu(btnZoomin);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater menuInflater = getMenuInflater();

        if(v == btnRotation)
        {
            menu.setHeaderTitle("배경색 변경");
            menuInflater.inflate(R.menu.context_menu1, menu);
        }
        if(v == btnZoomin)
        {
            menu.setHeaderTitle("버튼 변경");
            menuInflater.inflate(R.menu.context_menu2, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
         super.onContextItemSelected(item);
         if(item.getItemId() == R.id.item_bg_orange)
         {
             linear.setBackgroundColor(Color.rgb(255,165,0));
             return true;
         } else if (item.getItemId() == R.id.item_bg_yellow )
         {
             linear.setBackgroundColor(Color.YELLOW);
             return true;
         } else if(item.getItemId() == R.id.item_bg_red )
         {
             linear.setBackgroundColor(Color.RED);
             return true;
         } else if(item.getItemId() == R.id.item_btn_rotation )
         {
             rotationDegree += 45.0f;   // 누를 때마다 45도 증가
             btnZoomin.setRotation(rotationDegree);
             return true;
         }  else if(item.getItemId() == R.id.item_btn_zoomin )
         {
             btnZoomin.setScaleX(2);
             return true;
         }
        return false;
    }
}