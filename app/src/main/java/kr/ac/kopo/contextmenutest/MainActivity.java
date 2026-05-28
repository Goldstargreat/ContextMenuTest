package kr.ac.kopo.contextmenutest;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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
    LinearLayout linear; // LinearLayout 타입의 변수를 linear라는 이름으로 선언

    float rotationDegree;   // 회전 각도

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
        btnRotation = findViewById(R.id.btn_bg); // "배경색 변경" 버튼 연결
        btnZoomin = findViewById(R.id.btn_change); // "버튼 변경" 버튼 연결
        linear = findViewById(R.id.main); // 전체 레이아웃 연결
        Button btnAlert = findViewById(R.id.btn_alert);
        btnAlert.setOnClickListener(new View.OnClickListener() // 대화상자 클릭 이벤트 등록
        {
            @Override
            public void onClick(View v)
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("배경색 변경");
                dialog.setMessage("배경색을 파란색으로 변경할까요?");
                dialog.setIcon(R.drawable.icon);
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
//                        Toast.makeText(getApplicationContext(),"확인 버튼을 클릭했어요.", Toast.LENGTH_SHORT).show();
                        linear.setBackgroundColor(Color.BLUE);
                    }
                });
                dialog.setNegativeButton("취소", null);
                dialog.show();
            }
        });

        registerForContextMenu(btnRotation); // 두 버튼에 컨텍스트 메뉴 등록
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
            menuInflater.inflate(R.menu.context_menu1, menu);  // context_menu1 표시 (배경색 변경)
        }
        if(v == btnZoomin)
        {
            menu.setHeaderTitle("버튼 변경");
            menuInflater.inflate(R.menu.context_menu2, menu); // context_menu2 표시 (버튼 변경)
        }
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item)
    {
         super.onContextItemSelected(item);
         if(item.getItemId() == R.id.item_bg_orange)
         {
             linear.setBackgroundColor(Color.rgb(255,165,0));
             return true; // 처리 완료, 이벤트 소비
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
             rotationDegree = rotationDegree + 45.0f;   // 누를 때마다 45도 증가
             btnZoomin.setRotation(rotationDegree);
             return true;
         }  else if (item.getItemId() == R.id.item_btn_zoomin)
         {
             float currentScale = btnZoomin.getScaleX();
             if (currentScale < 8.0f)
             {
                 btnZoomin.setScaleX(currentScale * 2);
             }
             return true;
         }
         else if (item.getItemId() == R.id.item_btn_zoomout)  // 추가
         {
             float currentScale = btnZoomin.getScaleX();
             if (currentScale > 0.25f)
             {
                 btnZoomin.setScaleX(currentScale / 2);
             }
             return true;
         }
        return false; // 조건이 맞는게 하나도 없으면 ❌ 처리 안 됨, 이벤트를 상위로 넘김
    }
}