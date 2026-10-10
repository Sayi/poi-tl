/*
 * Copyright 2014-2026 Sayi
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
package com.deepoove.poi.plugin.math.generator;

import org.openxmlformats.schemas.officeDocument.x2006.math.CTAcc;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTBar;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTBorderBox;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTBox;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTD;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTEqArr;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTF;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTFunc;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTGroupChr;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTLimLow;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTLimUpp;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTM;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTNary;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTPhant;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMath;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTOMathArg;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTR;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTRad;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTSPre;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTSSub;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTSSubSup;
import org.openxmlformats.schemas.officeDocument.x2006.math.CTSSup;

/**
 * The element factory shared by {@code m:oMath} and {@code m:oMathArg}.
 * <p>
 * Both schema types expose exactly the same {@code addNewXxx()} element methods,
 * so the generator can emit into either without duplication. Verified against
 * POI 5.4.0: the two method sets differ only in {@code addNewArgPr} /
 * {@code addNewCtrlPr}, which the generator does not need.
 *
 * @author Sayi
 */
abstract class OmmlContainer {

    static OmmlContainer of(CTOMath math) {
        return new MathContainer(math);
    }

    static OmmlContainer of(CTOMathArg arg) {
        return new ArgContainer(arg);
    }

    abstract CTR addRun();

    abstract CTF addFraction();

    abstract CTRad addRadical();

    abstract CTSSup addSuperscript();

    abstract CTSSub addSubscript();

    abstract CTSSubSup addSubSuperscript();

    abstract CTNary addNary();

    abstract CTD addDelimiter();

    abstract CTM addMatrix();

    abstract CTEqArr addEquationArray();

    abstract CTBar addBar();

    abstract CTAcc addAccent();

    abstract CTFunc addFunction();

    abstract CTLimLow addLowerLimit();

    abstract CTLimUpp addUpperLimit();

    abstract CTGroupChr addGroupChar();

    abstract CTBox addBox();

    abstract CTBorderBox addBorderBox();

    abstract CTPhant addPhantom();

    abstract CTSPre addPreScript();

    private static final class MathContainer extends OmmlContainer {

        private final CTOMath math;

        private MathContainer(CTOMath math) {
            this.math = math;
        }

        @Override CTR addRun() { return math.addNewR(); }

        @Override CTF addFraction() { return math.addNewF(); }

        @Override CTRad addRadical() { return math.addNewRad(); }

        @Override CTSSup addSuperscript() { return math.addNewSSup(); }

        @Override CTSSub addSubscript() { return math.addNewSSub(); }

        @Override CTSSubSup addSubSuperscript() { return math.addNewSSubSup(); }

        @Override CTNary addNary() { return math.addNewNary(); }

        @Override CTD addDelimiter() { return math.addNewD(); }

        @Override CTM addMatrix() { return math.addNewM(); }

        @Override CTEqArr addEquationArray() { return math.addNewEqArr(); }

        @Override CTBar addBar() { return math.addNewBar(); }

        @Override CTAcc addAccent() { return math.addNewAcc(); }

        @Override CTFunc addFunction() { return math.addNewFunc(); }

        @Override CTLimLow addLowerLimit() { return math.addNewLimLow(); }

        @Override CTLimUpp addUpperLimit() { return math.addNewLimUpp(); }

        @Override CTGroupChr addGroupChar() { return math.addNewGroupChr(); }

        @Override CTBox addBox() { return math.addNewBox(); }

        @Override CTBorderBox addBorderBox() { return math.addNewBorderBox(); }

        @Override CTPhant addPhantom() { return math.addNewPhant(); }

        @Override CTSPre addPreScript() { return math.addNewSPre(); }

    }

    private static final class ArgContainer extends OmmlContainer {

        private final CTOMathArg arg;

        private ArgContainer(CTOMathArg arg) {
            this.arg = arg;
        }

        @Override CTR addRun() { return arg.addNewR(); }

        @Override CTF addFraction() { return arg.addNewF(); }

        @Override CTRad addRadical() { return arg.addNewRad(); }

        @Override CTSSup addSuperscript() { return arg.addNewSSup(); }

        @Override CTSSub addSubscript() { return arg.addNewSSub(); }

        @Override CTSSubSup addSubSuperscript() { return arg.addNewSSubSup(); }

        @Override CTNary addNary() { return arg.addNewNary(); }

        @Override CTD addDelimiter() { return arg.addNewD(); }

        @Override CTM addMatrix() { return arg.addNewM(); }

        @Override CTEqArr addEquationArray() { return arg.addNewEqArr(); }

        @Override CTBar addBar() { return arg.addNewBar(); }

        @Override CTAcc addAccent() { return arg.addNewAcc(); }

        @Override CTFunc addFunction() { return arg.addNewFunc(); }

        @Override CTLimLow addLowerLimit() { return arg.addNewLimLow(); }

        @Override CTLimUpp addUpperLimit() { return arg.addNewLimUpp(); }

        @Override CTGroupChr addGroupChar() { return arg.addNewGroupChr(); }

        @Override CTBox addBox() { return arg.addNewBox(); }

        @Override CTBorderBox addBorderBox() { return arg.addNewBorderBox(); }

        @Override CTPhant addPhantom() { return arg.addNewPhant(); }

        @Override CTSPre addPreScript() { return arg.addNewSPre(); }

    }

}
