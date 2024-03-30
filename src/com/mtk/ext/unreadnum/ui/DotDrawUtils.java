/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mtk.ext.unreadnum.ui;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.Log;

import com.android.launcher3.icons.DotRenderer;
import com.android.launcher3.icons.DotRenderer.DrawParams;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static android.graphics.Paint.FILTER_BITMAP_FLAG;

/**
 * Used to draw a notification dot on top of an icon.
 */
public class DotDrawUtils {

    private static final String TAG = "Unread DotRendererExt";

    private static final String BLANK_STRING = "  ";

    // The dot size is defined as a percentage of the app icon size.
    private static final float TEXT_SIZE_PERCENTAGE = 0.23f;
    private static final int TEXT_PADDING = 2;
    private static final float SHADOW_BLUR = 3f;
    private static final int MAX_NUM_LIMIT = 99;

    private static final Paint sDotPaint = new Paint(ANTI_ALIAS_FLAG | FILTER_BITMAP_FLAG);
    private static final Paint sTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static int TEXT_BG_COLOR = Color.RED;
    private static int TEXT_COLOR = Color.WHITE;

    /**
     * Draw a circle on top of the canvas according to the given params.
     */
    public static void draw(Canvas canvas, DotNumParams params) {
        if (params == null || params.dotNum <= 0) {
            Log.e(TAG, "Invalid null argument(s) passed in call to draw.");
            return;
        }
        // init text
        initTextPaint(params.iconSizePx);
        String text = dotNumToString(params.dotNum);
        Rect textBounds = getTextBounds(text);
        Rect dotRect = getDotRect(textBounds);
        Point textDrawPoint = getTextDrawPoint(dotRect);

        canvas.save();
        Rect iconBounds = params.drawParams.iconBounds;
        float dotCenterX = iconBounds.left + iconBounds.width() * params.position[0];
        float dotCenterY = iconBounds.top + iconBounds.height() * params.position[1];

        // Ensure dot fits entirely in canvas clip bounds.
        Rect canvasBounds = canvas.getClipBounds();
        float offsetX = params.drawParams.leftAlign
                ? Math.max(0, canvasBounds.left - Math.round(dotCenterX - dotRect.centerX()))
                : Math.min(0, canvasBounds.right - Math.round(dotCenterX + dotRect.centerX()));
        float offsetY = Math.max(0, canvasBounds.top - (dotCenterY - dotRect.centerY()));

        // We draw the dot relative to its center.
        canvas.translate(dotCenterX + offsetX - dotRect.centerX() - SHADOW_BLUR,
                dotCenterY + offsetY - dotRect.centerY() + SHADOW_BLUR);
        canvas.scale(params.drawParams.scale, params.drawParams.scale);

        sDotPaint.setShadowLayer(SHADOW_BLUR, 0, 0, Color.DKGRAY);
        sDotPaint.setColor(TEXT_BG_COLOR);
        canvas.drawRoundRect(new RectF(dotRect), dotRect.centerY(), dotRect.centerY(), sDotPaint);
        canvas.drawText(text, textDrawPoint.x, textDrawPoint.y, sTextPaint);
        canvas.restore();
    }

    private static String dotNumToString(int num) {
        String text = "";
        if (num > 0 && num <= 99) {
            text = String.valueOf(num);
        } else if (num > 99) {
            text = MAX_NUM_LIMIT + "+";
        }
        return text;
    }

    private static Rect getTextBounds(String text) {
        // init text bounds
        Paint.FontMetrics fm = sTextPaint.getFontMetrics();
        Rect textBounds = new Rect();
        textBounds.left = 0;
        textBounds.top = 0;
        textBounds.right = Math.round(sTextPaint.measureText(text));
        textBounds.bottom = Math.round(fm.descent - fm.ascent);
        if (textBounds.width() <= textBounds.height()) {
            textBounds.right = textBounds.bottom;
        } else {
            textBounds.right += Math.round(sTextPaint.measureText(BLANK_STRING));
        }
        return textBounds;
    }

    private static Rect getDotRect(Rect textBounds) {
        Rect dotRect = new Rect();
        dotRect.left = 0;
        dotRect.top = 0;
        dotRect.right = textBounds.right + TEXT_PADDING * 2;
        dotRect.bottom = textBounds.bottom + TEXT_PADDING * 2;
        return dotRect;
    }

    private static Point getTextDrawPoint(Rect dotRect) {
        Point p = new Point();
        Paint.FontMetrics fm = sTextPaint.getFontMetrics();
        int fontHeight = Math.round(fm.descent - fm.ascent);
        int paddingY = (dotRect.height() - fontHeight) >> 1;
        p.x = dotRect.centerX();
        p.y = dotRect.top + paddingY + Math.abs(Math.round(fm.ascent));
        return p;
    }

    private static void initTextPaint(int iconSize) {
        sTextPaint.setTextSize((iconSize * TEXT_SIZE_PERCENTAGE) - 1);
        Typeface font = Typeface.create("sans-serif", Typeface.BOLD);
        sTextPaint.setTypeface(font);
        sTextPaint.setTextAlign(Paint.Align.CENTER);
        sTextPaint.setAntiAlias(true);
        sTextPaint.setColor(TEXT_COLOR);
    }

    public static class DotNumParams {
        float[] position;
        int iconSizePx;
        DrawParams drawParams;
        int dotNum;

        public DotNumParams(DotRenderer dotRenderer, int iconSizePx, DrawParams params, int dotNum) {
            this.iconSizePx = iconSizePx;
            position = params.leftAlign ? dotRenderer.getLeftDotPosition()
                    : dotRenderer.getRightDotPosition();
            drawParams = params;
            this.dotNum = dotNum;
        }
    }
}