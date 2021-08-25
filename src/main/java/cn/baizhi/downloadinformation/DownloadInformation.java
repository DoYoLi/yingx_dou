package cn.baizhi.downloadinformation;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.entity.User;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class DownloadInformation {
    //下载用户信息
    public void down(List<User> list) throws IOException {
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("应学用户", "用户详细信息"), User.class, list);
        workbook.write(new FileOutputStream(new File("E:/yingxue/yingxue.xls")));
        workbook.close();
    }
}
