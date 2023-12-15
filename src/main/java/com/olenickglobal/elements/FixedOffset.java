package com.olenickglobal.elements;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

public record FixedOffset(Alignment origin, int x, int y) implements Offset {
    private static final Map<Alignment, Function<Rectangle, Point>> COORDINATES_FOR_ALIGNMENT = new EnumMap<>(Alignment.class) {{
        put(Alignment.BOTTOM, r -> new Point((int) r.getCenterX(), (int) r.getMaxY()));
        put(Alignment.BOTTOM_LEFT, r -> new Point((int) r.getMinX(), (int) r.getMaxY()));
        put(Alignment.BOTTOM_RIGHT, r -> new Point((int) r.getMaxX(), (int) r.getMaxY()));
        put(Alignment.CENTER, r -> new Point((int) r.getCenterX(), (int) r.getCenterY()));
        put(Alignment.LEFT, r -> new Point((int) r.getMinX(), (int) r.getCenterY()));
        put(Alignment.RIGHT, r -> new Point((int) r.getMaxX(), (int) r.getCenterY()));
        put(Alignment.TOP, r -> new Point((int) r.getCenterX(), (int) r.getMinY()));
        put(Alignment.TOP_LEFT, r -> new Point((int) r.getMinX(), (int) r.getMinY()));
        put(Alignment.TOP_RIGHT, r -> new Point((int) r.getMaxX(), (int) r.getMinY()));
    }};

    @Override
    public Point apply(Rectangle rectangle) {
        Point target = COORDINATES_FOR_ALIGNMENT.get(origin).apply(rectangle);
        target.translate(x, y);
        return target;
    }
}
