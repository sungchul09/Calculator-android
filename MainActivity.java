package achro4.fpga.tester;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import android.view.View.OnClickListener;
//import android.widget.TextView;
//public class FpgaBuzzerActivity extends Activity{

public class MainActivity extends Activity {

    EditText edit; // 상단에 숫자값과 결과가 보여질 EditText

    Button division, plus, equal, multi, sub; //연산자, 익명내부클래스
    Button cancel; //익명내부클래스의임시객체

    String number;
    //첫번째 값  -> 연산자 -> 두번째 값 순서로 진행될 때, 첫번째 값을 담아 둘 곳.
    //int나 double형으로 해도 되는데 나는 연산할 때 한번에 Parse 써서 할거라서 그냥 String으로 함.

    int value; //어떤 연산자가 선택되었는지. 각각 연산자에 대한 버튼이 눌리면, value에 0~3까지 값이 들어가서 어떤 놈이 선택되어 저장되었는지 확인 가능하게 했음

    int DIVISION = 0;
    int PLUS = 1;
    int MULTI = 2;
    int SUB = 3;
    double a=0, b=0,c=0;
    char d;
    Handler mHandler = new Handler();
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        edit = (EditText)findViewById(R.id.edit);
        number = "";

        /** 연산자 + - / * = 는 익명 이너 클래스를 사용한다. mListener를 만들어서 연동시켜준다.  **/
        division = (Button)findViewById(R.id.btn_division); // /
        plus = (Button)findViewById(R.id.btn_plus); // +
        equal = (Button)findViewById(R.id.btn_result); // =
        sub = (Button)findViewById(R.id.btn_sub); // -
        multi = (Button)findViewById(R.id.btn_multi); // *

        division.setOnClickListener(mListener);
        plus.setOnClickListener(mListener);
        equal.setOnClickListener(mListener);
        sub.setOnClickListener(mListener);
        multi.setOnClickListener(mListener);


        /** 익명 이너클래스 내부 객체 사용*/
        cancel = (Button)findViewById(R.id.btn_cancel); // C
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                number = "";
                edit.setText("");
            }
        });
    }

    /** 연산자 용 익명 내부 클래스.*/
    Button.OnClickListener mListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            EqualThread equalThread = new EqualThread();
            mHandler = new Handler();
            switch(v.getId()){
                case R.id.btn_division :
                    number = edit.getText().toString();
                    a=Double.parseDouble(number); // 첫번째로 입력했던 녀석들 저장해둠. editText가 clear될거니까.
                    edit.setText("");//내용물비워주기
                    value = DIVISION;
                    Toast.makeText(MainActivity.this, "/", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_plus :
                    number = edit.getText().toString();
                    a=Double.parseDouble(number);
                    edit.setText("");//내용물비워주기
                    value = PLUS;
                    Toast.makeText(MainActivity.this, "+", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_sub :
                    number = edit.getText().toString();
                    a=Double.parseDouble(number);
                    edit.setText("");
                    value = SUB;
                    Toast.makeText(MainActivity.this, "-", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_multi :
                    number = edit.getText().toString();
                    a=Double.parseDouble(number);
                    edit.setText("");
                    value = MULTI;
                    Toast.makeText(MainActivity.this, "*", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.btn_result :
                    //FpgaSetBuzzerValue(1); //buzzer
                    //FpgaSetMotorState(1,1,20); //motor
                    //FpgaSetLedValue(1); // led All On

                    //FpgaSetFndValue("10");//fnd set
                    //FpgaSetOnOff(1);//fnd start ?
                    //FpgaSetTextLcdValue("start...", "good luck!");// text lcd start
                    
                    if (value == MULTI) {
                        b=Double.parseDouble(edit.getText().toString());
                        d='*';
                        edit.setText(Double.parseDouble(number)+" * "+ Double.parseDouble(edit.getText().toString()));
                        equalThread.start();
                    } else if (value == SUB){
                        b=Double.parseDouble(edit.getText().toString());
                        d='-';
                        edit.setText(Double.parseDouble(number)+" - "+ Double.parseDouble(edit.getText().toString()));
                        equalThread.start();
                    } else if (value == PLUS){
                        b=Double.parseDouble(edit.getText().toString());
                        d='+';
                        edit.setText(Double.parseDouble(number)+" + "+ Double.parseDouble(edit.getText().toString()));
                        equalThread.start();
                    } else if (value == DIVISION){
                        b=Double.parseDouble(edit.getText().toString());
                        d='/';
                        edit.setText(Double.parseDouble(number)+" / "+ Double.parseDouble(edit.getText().toString()));
                        equalThread.start();
                    }
                    number = edit.getText().toString(); // 최종 결과값을 가지고, 바로 다음 연산을 가능하게 하도록 number에 들어가 있는 값을 교체해준다.
                    break;
            }
        }


    };

    // xml 에서 onClick 이벤트를 연동해놓았던 숫자들입니다.
    public void onClick (View v)
    {
        switch(v.getId()){
            case R.id.btn_0 : edit.setText(edit.getText().toString() + 0); break;
            case R.id.btn_1 : edit.setText(edit.getText().toString() + 1); break;
            case R.id.btn_2 : edit.setText(edit.getText().toString() + 2); break;
            case R.id.btn_3 : edit.setText(edit.getText().toString() + 3); break;
            case R.id.btn_4 : edit.setText(edit.getText().toString() + 4); break;
            case R.id.btn_5 : edit.setText(edit.getText().toString() + 5); break;
            case R.id.btn_6 : edit.setText(edit.getText().toString() + 6); break;
            case R.id.btn_7 : edit.setText(edit.getText().toString() + 7); break;
            case R.id.btn_8 : edit.setText(edit.getText().toString() + 8); break;
            case R.id.btn_9 : edit.setText(edit.getText().toString() + 9); break;
        }
    }
    

    public class EqualThread extends Thread{
        public void run() {
        	int cnt=0;
            while(true){
                try {
                    //1/1000초 단위로 입력하면 해당 시간동안 non-runnable상태로 빠지게 된다.
                    //1000을 입력하면 1초 쉰다.
                	 FpgaSetMotorState(1,1,20); //motor
                	 FpgaSetTextLcdValue("start...", "good luck!");// text lcd start
                	 
                	 for(cnt=0; cnt<10; cnt++)
                	 {
                		 FpgaSetDotValue(cnt+1);
                		if(cnt==6)
                		{
                			FpgaSetBuzzerValue(1);
                		}	 
                		FpgaSetLedValue(cnt);
                		Thread.sleep(1000);
                		if(cnt==9){
                			FpgaSetBuzzerValue(0);
                		}
                	 }
                	 Thread.sleep(1000);
                	 FpgaSetBuzzerValue(0);
                	 FpgaSetDotValue(11);
                	 FpgaSetMotorState(0,1,20); //motor
                	 FpgaSetLedValue(8); // led All Off
                	 FpgaSetTextLcdValue("enter your", "calculation");// text lcd stop
               
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mHandler.post(new Runnable() {             
                    public void run() {
                        // UI 작업 수행 O
                        if(d=='*') {
                            edit.setText(Double.toString(a * b));
                        } else if(d=='/'){
                            edit.setText(Double.toString(a / b));
                        } else if(d=='+'){
                            edit.setText(Double.toString(a + b));
                        } else if(d=='-'){
                            edit.setText(Double.toString(a - b));
                        }
                    }
                });


                break;
            }
        }
    }
    
    
    
    
    

    public native int FpgaSetBuzzerValue(int x);
    public native int FpgaSetTextLcdValue(String ptr1, String ptr2);
    public native String FpgaSetMotorState(int x, int y, int z);
    public native String FpgaSetLedValue(int x);
    public native String FpgaSetDotValue(int x);
    
    static {
        System.loadLibrary("achro4-device");
    }


}