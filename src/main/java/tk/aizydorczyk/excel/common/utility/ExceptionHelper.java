package tk.aizydorczyk.excel.common.utility;

import java.util.function.Supplier;

public final class ExceptionHelper {

	private ExceptionHelper() {
	}

	public static <T, E extends RuntimeException> T getOrRethrowException(ThrowingGetter<T, E> throwingGetter, Supplier<E> exceptionSupplier) {
		return throwingGetter.getAndRethrowWithCustomException(exceptionSupplier);
	}

	public static <T, E extends RuntimeException> T getOrRethrowException(ThrowingGetter<T, E> throwingGetter) {
		return throwingGetter.getAndRethrow();
	}

	public static <E extends RuntimeException> void executeOrRethrowException(ThrowingExecutor<E> function, Supplier<E> exceptionSupplier) {
		function.executeAndRethrowWithCustomException(exceptionSupplier);
	}

	@FunctionalInterface
	public interface ThrowingGetter<T, E extends RuntimeException> {

		default T getAndRethrowWithCustomException(Supplier<E> exceptionSupplier) {
			try {
				return getThrows();
			} catch (final Exception ex) {
				E customException = exceptionSupplier.get();
				customException.addSuppressed(ex);
				throw customException;
			}
		}

		default T getAndRethrow() {
			try {
				return getThrows();
			} catch (final Exception ex) {
				throw new RuntimeException(ex);
			}
		}

		T getThrows() throws Exception;
	}

	@FunctionalInterface
	public interface ThrowingExecutor<E extends RuntimeException> {

		default void executeAndRethrowWithCustomException(Supplier<E> exceptionSupplier) {
			try {
				executeThrows();
			} catch (final Exception ex) {
				E customException = exceptionSupplier.get();
				customException.addSuppressed(ex);
				throw customException;
			}
		}

		void executeThrows() throws Exception;
	}
}