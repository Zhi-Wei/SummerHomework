package tw.edu.ntou.summerHomework.RssReader.common;

import java.util.function.Supplier;

// 在目前 RssReader 類別的使用情形上，型別參數：TResult 是 Iterable<RssDocument>。
public class Retrier<TResult> {

	public TResult Try(Supplier<TResult> func, int maxRetries) throws Exception {
		return this.TryWithDelay(func, maxRetries, 0);
	}

	public TResult TryWithDelay(Supplier<TResult> func, int maxRetries, int delayInMilliseconds) throws Exception {
		TResult returnValue = null;
		int numTries = 0;
		boolean succeeded = false;

		while (numTries < maxRetries) {
			try {
				// 在目前 RssReader 類別的使用情形上，
				// 傳入參數：Supplier<TResult> func 方式是使用：
				// () -> this.getRssDocuments(url) 的一個匿名委派。
				returnValue = func.get();
				succeeded = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				numTries++;
			}

			if (succeeded) {
				return returnValue;
			}

			try {
				Thread.sleep(delayInMilliseconds);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		throw new Exception("執行方法失敗。");
	}
}
