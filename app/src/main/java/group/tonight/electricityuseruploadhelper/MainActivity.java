package group.tonight.electricityuseruploadhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.upload).setOnClickListener(this);
        findViewById(R.id.upload1022).setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mBaseQuickAdapter);
        mBaseQuickAdapter.setOnItemClickListener(this);

        try {
            String[] strings = getAssets().list("");
            mBaseQuickAdapter.replaceData(Arrays.asList(strings));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload:

                break;
            case R.id.upload1022:

                break;
            default:
                break;
        }
    }

    private BaseQuickAdapter<String, BaseViewHolder> mBaseQuickAdapter = new BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1) {
        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(android.R.id.text1, item);
        }
    };

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        String item = mBaseQuickAdapter.getItem(position);
        if (item == null) {
            return;
        }
        if (item.contains("定")) {
            upload1022(item);
        } else {
            uploadInit(item);
        }

    }

    /**
     * 总2673条数据
     *
     * @param fileName
     */
    private void uploadInit(final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Workbook workbook = Workbook.getWorkbook(getAssets().open(fileName));
                    Sheet sheet = workbook.getSheet(0);
                    int rows = sheet.getRows();//行
                    int columns = sheet.getColumns();//列
                    String[] keys = {
                            "userId"
                            , "userName"
                            , "userPhone"
                            , "powerLineId"
                            , "powerLineName"
                            , "meterReadingDay"
                            , "meterReader"
                            , "measurementPointId"
                            , "meterReadingId"
                            , "powerMeterId"
                            , "powerValueType"
                            , "lastPowerValue"
                            , "currentPowerValue"
                            , "consumePowerValue"
                            , "comprehensiveRatio"
                            , "meterReadingNumber"
                            , "exceptionTypes"
                            , "meterReadingStatus"
                            , "powerSupplyId"
                            , "powerSupplyName"
                            , "userAddress"
                    };

                    JSONArray jsonArray = new JSONArray();
                    for (int row = 0; row < rows; row++) {//行
                        if (row == 0) {
                            continue;
                        }
                        JSONObject jsonObject = new JSONObject();
                        for (int column = 0; column < columns; column++) {//列
                            //sheet.getCell(列，行);
                            String key = sheet.getCell(column, 0).getContents();
                            String value = sheet.getCell(column, row).getContents();
//                            jsonObject.put(key, value);
                            jsonObject.put(keys[column], value);
                        }
                        jsonArray.put(jsonObject);
                    }
                    String json = jsonArray.toString();
                    OkGo
                            .<String>post("http://192.168.0.211:8080/feehelper/user/batchAdd")
//                            .<String>post("http://tonight.group:8080/feehelper/user/batchAdd")
                            .upJson(jsonArray)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (IOException | BiffException | JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }

    private void upload1022(final String fileName) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Workbook workbook = Workbook.getWorkbook(getAssets().open(fileName));
                    Sheet sheet = workbook.getSheet(0);
                    int rows = sheet.getRows();//行
                    int columns = sheet.getColumns();//列
                    String[] keys = {
                            "userId"
                            , "userName"
                            , "userPhone"
                            , "userAddress"
                            , "powerMeterId"
                            , "meterReadingId"
                            , "powerLineId"
                    };

                    JSONArray jsonArray = new JSONArray();
                    for (int row = 0; row < rows; row++) {//行
                        if (row == 0) {
                            continue;
                        }
                        JSONObject jsonObject = new JSONObject();
                        for (int column = 0; column < keys.length; column++) {//列
                            //sheet.getCell(列，行);
                            String key = sheet.getCell(column, 0).getContents();
                            String value = sheet.getCell(column, row).getContents();
//                            jsonObject.put(key, value);
                            jsonObject.put(keys[column], value);
                        }
                        jsonArray.put(jsonObject);
                    }
                    String json = jsonArray.toString();
                    OkGo
                            .<String>post("http://192.168.0.211:8080/feehelper/user/batchAdd")
//                            .<String>post("http://tonight.group:8080/feehelper/user/batchAdd")
                            .upJson(jsonArray)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {
                                    Toast.makeText(MainActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } catch (IOException | BiffException | JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
    }
}
