/*
 * Copyright 2014-2020 Sayi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.deepoove.poi.data;

import java.util.ArrayList;
import java.util.List;

import com.deepoove.poi.data.style.TableV2Style;

/**
 * Data for table template
 * 
 * @author Sayi
 */
public class TableRenderData implements RenderData {

    private static final long serialVersionUID = 1L;

    private List<RowV2RenderData> rows = new ArrayList<>();
    private TableV2Style tableStyle;

    MergeCellRule mergeRule;

    public List<RowV2RenderData> getRows() {
        return rows;
    }

    public void setRows(List<RowV2RenderData> rows) {
        this.rows = rows;
    }

    public TableV2Style getTableStyle() {
        return tableStyle;
    }

    public void setTableStyle(TableV2Style tableStyle) {
        this.tableStyle = tableStyle;
    }

    public MergeCellRule getMergeRule() {
        return mergeRule;
    }

    public void setMergeRule(MergeCellRule mergeRule) {
        this.mergeRule = mergeRule;
    }

    public TableRenderData addRow(RowV2RenderData row) {
        rows.add(row);
        return this;
    }

    public int obtainColSize() {
        if (null == rows || rows.isEmpty()) return 0;
        RowV2RenderData row = rows.get(0);
        List<CellV2RenderData> cells = row.getCells();
        if (null == cells || cells.isEmpty()) return 0;
        return cells.size();
    }

    public int obtainRowSize() {
        if (null == rows || rows.isEmpty()) return 0;
        return rows.size();
    }

}
