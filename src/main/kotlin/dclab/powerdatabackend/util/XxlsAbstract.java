package dclab.powerdatabackend.util;


import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class XxlsAbstract extends DefaultHandler {
    private SharedStringsTable sst;
    private String lastContents;
    private int sheetIndex = -1;
    private List<String> rowlist = new ArrayList<>();
    public List<Map<String, Object>> dataMap = new LinkedList<>();  //即将进行批量插入的数据
    public int willSaveAmount;  //将要插入的数据量
    public int totalSavedAmount; //总共插入了多少数据
    private int curRow = 0;        //当前行
    private int curCol = 0;        //当前列索引
    private int preCol = 0;        //上一列列索引
    private int titleRow = 0;    //标题行，一般情况下为0
    public int rowsize = 0;    //列数

    //excel记录行操作方法，以sheet索引，行索引和行元素列表为参数，对sheet的一行元素进行操作，元素为String类型
    public abstract void optRows(int sheetIndex, int curRow, List<String> rowlist) throws SQLException;

    //只遍历一个sheet，其中sheetId为要遍历的sheet索引，从1开始，1-3

    /**
     * @param filename
     * @param sheetId  sheetId为要遍历的sheet索引，从1开始，1-3
     * @throws Exception
     */
    public void processOneSheet(String filename, int sheetId) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        // rId2 found by processing the Workbook
        // 根据 rId# 或 rSheet# 查找sheet
        InputStream sheet2 = r.getSheet("rId" + sheetId);
        sheetIndex++;
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst)
            throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader();
        this.sst = sst;
        parser.setContentHandler(this);
        return parser;
    }
    public void characters(char[] ch, int start, int length) {
        lastContents += new String(ch, start, length);
    }
    public void endElement(String uri, String localName, String name) {
        // 根据SST的索引值的到单元格的真正要存储的字符串
        try {
            int idx = Integer.parseInt(lastContents);
            lastContents = new XSSFRichTextString(sst.getEntryAt(idx))
                    .toString();
        } catch (Exception e) {

        }

        // v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
        // 将单元格内容加入rowlist中，在这之前先去掉字符串前后的空白符
        if (name.equals("v")) {
            String value = lastContents.trim();
            value = value.equals("") ? " " : value;
            int cols = curCol - preCol;
            if (cols > 1) {
                for (int i = 0; i < cols - 1; i++) {
                    rowlist.add(preCol, "");
                }
            }
            preCol = curCol;
            rowlist.add(curCol - 1, value);
        } else {
            //如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
            if (name.equals("row")) {
                int tmpCols = rowlist.size();
                if (curRow > this.titleRow && tmpCols < this.rowsize) {
                    for (int i = 0; i < this.rowsize - tmpCols; i++) {
                        rowlist.add(rowlist.size(), "");
                    }
                }
                try {
                    optRows(sheetIndex, curRow, rowlist);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (curRow == this.titleRow) {
                    this.rowsize = rowlist.size();
                }
                rowlist.clear();
                curRow++;
                curCol = 0;
                preCol = 0;
            }
        }
    }
}

