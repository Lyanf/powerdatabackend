package dclab.powerdatabackend.util;

import java.util.*;
import java.util.concurrent.*;

public class SaxExcelOp {
    public int addBlackLists(String file) throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newSingleThreadExecutor();
        ArrayList<Future<Integer>> resultList = new ArrayList<>();
        XxlsAbstract xxlsAbstract = new XxlsAbstract() {

            //针对数据的具体处理
            @Override
            public void optRows(int sheetIndex, int curRow, List<String> rowlist) {

                /**
                 * 判断即将插入的数据是否已经到达8000，如果到达8000，
                 * 进行数据插入
                 */
                if (this.willSaveAmount == 5000) {

                    //插入数据
                    List<Map<String, Object>> list = new LinkedList<>(this.dataMap);
                    Callable<Integer> callable = () -> {
                        int count = list.size();
//                        blacklistRecordMasterDao.addBlackListRecords(list);
                        System.out.println(count+"=============");
                        return count;
                    };
                    this.willSaveAmount = 0;
                    this.dataMap = new LinkedList<>();

                    Future<Integer> future = executor.submit(callable);
                    resultList.add(future);
                }

                //汇总数据
                Map<String, Object> map = new HashMap<>();
                map.put("uid", rowlist.get(0));
                map.put("createTime", rowlist.get(1));
                map.put("regGame", rowlist.get(2));
                map.put("banGame", rowlist.get(2));
                this.dataMap.add(map);
                this.willSaveAmount++;
                this.totalSavedAmount++;
            }
        };
        try {
            xxlsAbstract.processOneSheet(file, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //针对没有存入的数据进行处理
        if(xxlsAbstract.willSaveAmount != 0){
            List<Map<String, Object>> list = new LinkedList<>(xxlsAbstract.dataMap);
            Callable<Integer> callable = () -> {
                int count = list.size();
//                blacklistRecordMasterDao.addBlackListRecords(list);
                System.out.println(count+"=============");
                return count;
            };
            Future<Integer> future = executor.submit(callable);
            resultList.add(future);
        }

        executor.shutdown();
        int total = 0;
        for (Future<Integer> future : resultList) {
            while (true) {
                if (future.isDone() && !future.isCancelled()) {
                    int sum = future.get();
                    total += sum;
                    break;
                } else {
                    Thread.sleep(100);
                }
            }
        }
        return total;
    }
}
