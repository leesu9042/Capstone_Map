package com.example.capstone_map.route;



import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;
import com.skt.Tmap.TMapPoint;


import java.util.List;


import java.util.List;

public class RouteLineDrawer {

    /**
     * 지도에 경로 선을 그려줍니다.
     *
     * @param tMapView       지도 뷰 객체
     * @param routePoints    경로 좌표 리스트 (TMapPoint)
     * @param lineId         선의 ID (지도에서 식별용)
     * @param color          선 색깔 (예: Color.BLUE)
     * @param width          선 두께 (픽셀 단위)
     */
    public static void drawRouteLine(TMapView tMapView,
                                     List<TMapPoint> routePoints,
                                     String lineId,
                                     int color,
                                     float width) {

        TMapPolyLine polyLine = new TMapPolyLine();
        polyLine.setLineColor(color);
        polyLine.setLineWidth(width);

        for (TMapPoint point : routePoints) {
            polyLine.addLinePoint(point);
        }

        tMapView.addTMapPolyLine(lineId, polyLine);
    }
}
