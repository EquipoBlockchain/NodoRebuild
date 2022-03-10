/**
 * Copyright 2021 Kyle Elbjorn
 *
 * This file is part of GEHIRN Node.
 *
 * GEHIRN Node is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * GEHIRN Node is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with GEHIRN Node.
 * If not, see <https://www.gnu.org/licenses/>.
 */

package graphics

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

/**
 * TODO Document
 *
 *  Based of the compose-charts project by bytebeats in: [link](https://github.com/bytebeats/compose-charts)
 *
 *  Licence:
 *
 *  Copyright (c) 2021 Chen Pan
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 *  rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 *  permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 *  Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 *  OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

@Composable
fun pieChart(
    sliceList : SliceList
) {
    val progress = remember(
        sliceList.slice
    ) {
        Animatable(
            initialValue = 0F
        )
    }

    LaunchedEffect(
        sliceList.slice
    ) {
       progress.animateTo(
           targetValue   = 1F,
           animationSpec = TweenSpec(durationMillis = 1500)
       )
    }

    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        drawIntoCanvas {
            var arcStart = 270F

            val listValueSum = sliceList.slice.sumOf { it.value }

            sliceList.slice.forEach { slice ->
                val arcProgress = (slice.value * progress.value * 360 / listValueSum)
                val offset      = (size.width - size.height) / 2F

                drawContext.canvas.drawArc(
                    rect = Rect(
                        left   = offset,
                        top    = 0F,
                        right  = size.width - offset,
                        bottom = size.height
                    ),
                    paint = Paint().apply {
                        style       = PaintingStyle.Stroke
                        color       = slice.color
                        strokeWidth = 40F
                    },
                    startAngle = arcStart,
                    sweepAngle = arcProgress,
                    useCenter  = false
                )
                arcStart += arcProgress
            }
        }
    }
}