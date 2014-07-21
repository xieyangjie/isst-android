package cn.edu.zju.isst.tests.junit;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import cn.edu.zju.isst.data.CSTJsonDataDelegate;
import cn.edu.zju.isst.data.CSTJsonParser;
import cn.edu.zju.isst.model.CSTArchive;
import cn.edu.zju.isst.model.CSTJsonRaw;
import cn.edu.zju.isst.model.CSTUser;
import cn.edu.zju.isst.tests.junit.util.CSTFileUtil;
import cn.edu.zju.isst.tests.junit.util.CSTPrinter;

public class CSTJsonParserTest {

    @Test
    public void testReadSingleObject() {
        System.out.println("\n====" + this.getClass() + " : testReadSingleObject()====");
        File file = new File("data/user.json");
        byte[] data = CSTFileUtil.fileToByte(file);
        CSTUser user = null;
        try {
            user = CSTJsonParser.readByte(data, CSTUser.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CSTPrinter.out(user);

        file = new File("data/archive.json");
        data = CSTFileUtil.fileToByte(file);
        CSTArchive archive = null;
        CSTJsonRaw jsonRaw = null;
        try {
            jsonRaw = CSTJsonParser.readByte(data, CSTJsonRaw.class);
            archive = CSTJsonParser.readJsonObject(jsonRaw.getBody(), CSTArchive.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CSTPrinter.out(archive);
        System.out.println("====end====\n");
    }

    @Test
    public void testReadObjectList() {
        System.out.println("\n====" + this.getClass() + " : testReadObjectList()====");
        File file = new File("data/archives.json");
        byte[] data = CSTFileUtil.fileToByte(file);
        List<CSTArchive> archiveList = null;
//        for (CSTArchive archive : archiveList) {
//            CSTPrinter.out(archive);
//            CSTPrinter.out(archive.getPublisher());
//        }
        System.out.println("====end====\n");
    }

}
