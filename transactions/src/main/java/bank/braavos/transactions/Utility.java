package bank.braavos.transactions;

public enum Utility {
    INSTANCE;

    public static void injectField(final Object injectable,
                                   final String fieldname,
                                   final Object value) {

        try {

            final java.lang.reflect.Field field = injectable.getClass()
                    .getDeclaredField(fieldname);

            final boolean origionalValue = field.isAccessible();
            field.setAccessible(true);
            field.set(injectable, value);
            field.setAccessible(origionalValue);

        } catch (final NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e.getMessage(), e);
        }

    }

}
