/*
 * Copyright (C) 2021 Yunify, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this work except in compliance with the License.
 * You may obtain a copy of the License in the LICENSE file, or at:
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qingstor.sdk.exception;

import java.io.EOFException;
import org.junit.Assert;
import org.junit.Test;

public class QSExceptionTest {

    @Test
    public void testMessageWithMessageOnly() {
        QSException e = new QSException("test error");
        Assert.assertEquals("Error Code: 0; Error Message: test error", e.getMessage());
    }

    @Test
    public void testMessageWithMessageAndCause() {
        Exception cause = new Exception("cause error");
        QSException e = new QSException("wrapper error", cause);
        Assert.assertEquals(
                "Error Code: 0; Error Message: wrapper error\ncause error", e.getMessage());
    }

    @Test
    public void testMessageWithCauseHavingNullMessage() {
        EOFException cause = new EOFException();
        QSException e = new QSException("EOFException", cause);
        Assert.assertEquals("Error Code: 0; Error Message: EOFException", e.getMessage());
    }

    @Test
    public void testMessageWithNullMessageAndCause() {
        EOFException cause = new EOFException();
        QSException e = new QSException(null, cause);
        Assert.assertEquals("Error Code: 0; Error Message: java.io.EOFException", e.getMessage());
    }

    @Test
    public void testMessageWithThrowableConstructor() {
        NullPointerException cause = new NullPointerException();
        QSException e = new QSException(cause);
        Assert.assertEquals(
                "Error Code: 0; Error Message: java.lang.NullPointerException", e.getMessage());
    }

    @Test
    public void testMessageWithNullMessageAndNullCause() {
        QSException e = new QSException((String) null);
        Assert.assertEquals("Error Code: 0; Error Message: Unknown", e.getMessage());
    }
}
