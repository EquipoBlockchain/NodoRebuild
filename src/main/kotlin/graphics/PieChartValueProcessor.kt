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

import androidx.compose.ui.graphics.Color
import live.feed.ProcessResults
import live.processResults

class PieChartValueProcessor {
    val colors = mutableListOf(
        Color(0XFFFF3333),
        Color(0XFFFFA333),
        Color(0XFFF8FF33),
        Color(0XFFBEFF5C),
        Color(0XFF55FF33),
        Color(0XFF33EEFF),
        Color(0XFF3381FF),
        Color(0XFF5233FF),
        Color(0XFFFF70FF),
        Color(0XFFFF33F8)
    )

    var values = processResults.map(ProcessResults::totalVotes)

    var pieChartData = SliceList(
        slice = values.mapIndexed { index, value -> Slice(value, colors[index])}
    )
}