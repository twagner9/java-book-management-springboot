package com.twag.book_management_app.model;

public class Assert {
    public static void assertEquals(Object expected, Object actual, String message) {
        if (expected == null && actual == null) {
            return;
        }
        if (expected == null || actual == null) {
            throw new AssertionError (message);
        }
        if (!(expected.equals(actual))) {
            throw new AssertionError(message);
        }
    }

    public static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertFalse(boolean condition, String message) {
        if (condition) {
            throw new AssertionError(message);
        }
    }

    public static void assertNotNull(Object object, String message) {
        if (object == null) {
            throw new AssertionError(message);
        }
    }

//    private void checkAssertion(Runnable assertion) {
//        try {
//            assertion.run();
//        } catch (AssertionError e) {
//            errorMessages.add(e.getMessage());
//        }
//    }
}
