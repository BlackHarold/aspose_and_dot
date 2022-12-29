package ru.bluewhale.diagram;

import org.atpfivt.ljv.LJV;

import java.awt.*;
import java.net.URI;
import java.net.URLEncoder;
import java.util.Map;

public class LJVMain {

        public static void main(String[] args) {
            browse(new LJV(), Map.of(1, 'a', 2, 'b'));
        }

        public static void browse(LJV ljv, Object obj) {
            try {
                var dot = URLEncoder.encode(ljv.drawGraph(obj), "UTF8")
                        .replaceAll("\\+", "%20");
                Desktop.getDesktop().browse(
                        new URI("https://dreampuf.github.io/GraphvizOnline/#"
                                + dot));
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
    }
