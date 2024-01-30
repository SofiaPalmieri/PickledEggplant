package com.olenickglobal.formatting;

import com.olenickglobal.elements.ContainerElement;
import com.olenickglobal.elements.ImageElement;
import com.olenickglobal.elements.RectangleElement;
import com.olenickglobal.elements.TextElement;


import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;


public class ElementFormatter {

    public String formatRectangle(Rectangle rectangle) {
        return String.format("(x=%d,y=%d,w=%d,h=%d)",rectangle.x,rectangle.y,rectangle.width,rectangle.height);
    }

    public String formatRectangleElement(RectangleElement rectangleElement) {
        return String.format("Rectangle bound by %s",this.formatRectangle(rectangleElement.getScreen().getBounds()));
    }

    public String formatImageElement(ImageElement imageElement) {
        return String.format("Image: %s", imageElement.getName());
    }

    public String formatTextElement(TextElement textElement) {
        return String.format("Text: %s",textElement.getElementText());
    }

    public String formatContainerElement(ContainerElement containerElement) {
        List<String> elements = containerElement.getContents().stream()
                .map(s -> s.formatBy(this))
                .collect(Collectors.toList());
        String joinedElements = String.join(", ", elements);
        return String.format("Element bound by elements: %s", joinedElements);
    }

}
