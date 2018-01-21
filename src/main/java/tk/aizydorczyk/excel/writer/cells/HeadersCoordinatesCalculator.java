package tk.aizydorczyk.excel.writer.cells;

import tk.aizydorczyk.excel.common.exceptions.ExcelWriterException;
import tk.aizydorczyk.excel.common.messages.Messages;
import tk.aizydorczyk.excel.common.model.Header;

import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import static tk.aizydorczyk.excel.common.messages.Messages.NO_BOTTOM_HEADERS;
import static tk.aizydorczyk.excel.common.messages.Messages.NO_DATA_HEADERS;
import static tk.aizydorczyk.excel.common.messages.Messages.NO_MAIN_HEADER;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.notSetBefore;
import static tk.aizydorczyk.excel.common.utility.WriterHelper.selectMainHeaderOrThrow;

final class HeadersCoordinatesCalculator {

	private final List<Header> calculatedHeaders;
	private final int firstDataRowPosition;

	HeadersCoordinatesCalculator(List<Header> headers) {
		this.calculatedHeaders = calculate(headers);
		this.firstDataRowPosition = getFirstDataRowPosition(headers);
	}

	private List<Header> calculate(List<Header> headers) {
		calculateColumnPositions(headers);

		final Header mainHeader = selectMainHeaderOrThrow(headers, () ->
				new CoordinatesCalculateFail(NO_MAIN_HEADER));

		calculateBottomRowsPosition(mainHeader);
		calculateMainHeaderColumnPosition(mainHeader);

		alignDataHeadersRowPosition(headers);
		return headers;
	}

	private void calculateColumnPositions(List<Header> headers) {
		calculateColumnPositionsOfDataHeaders(headers);
		calculateColumnPositionsOfRestHeadersByDataHeadersPositions(headers);
	}

	private void calculateColumnPositionsOfDataHeaders(List<Header> headers) {
		final List<Header> dataHeaders = headers
				.stream()
				.filter(Header::isOverData)
				.collect(Collectors.toList());

		int columnIndex = 0;
		for (Header dataHeader : dataHeaders) {
			dataHeader.setStartColumnPosition(columnIndex);
			dataHeader.setEndColumnPosition(columnIndex);
			columnIndex++;
		}
	}

	private void calculateColumnPositionsOfRestHeadersByDataHeadersPositions(List<Header> headers) {
		final List<Header> notOverDataHeaders = headers.stream()
				.filter(Header::notOverData)
				.collect(Collectors.toList());

		calculateStartAndEndColumnPositions(notOverDataHeaders);
	}

	private void calculateStartAndEndColumnPositions(List<Header> notOverDataHeaders) {
		for (Header header : notOverDataHeaders) {
			if (notSetBefore(header.getStartColumnPosition())) {
				calculateStartColumnPositions(header);
			}
			calculateEndColumnPosition(header);
		}
	}

	private void calculateStartColumnPositions(Header header) {
		final Header firstBottomHeader = header.getBottomHeaders().stream()
				.findFirst()
				.orElseThrow(() ->
						new CoordinatesCalculateFail(NO_BOTTOM_HEADERS));

		if (notSetBefore(firstBottomHeader.getStartColumnPosition())) {
			calculateStartColumnPositions(firstBottomHeader);
			header.setStartColumnPosition(firstBottomHeader.getStartColumnPosition());
		} else {
			header.setStartColumnPosition(firstBottomHeader.getStartColumnPosition());
		}
	}

	private void calculateEndColumnPosition(Header header) {
		final int endColumnPosition = header.getStartColumnPosition() + header.getWidth() - 1;
		header.setEndColumnPosition(endColumnPosition);
	}

	private void calculateBottomRowsPosition(Header header) {
		for (Header bottomHeader : header.getBottomHeaders()) {
			if (notSetBefore(header.getRowPosition())) {
				header.setRowPosition(0);
			}

			bottomHeader.setRowPosition(header.getRowPosition() + 1);
			calculateBottomRowsPosition(bottomHeader);
		}
	}

	private void calculateMainHeaderColumnPosition(Header mainHeader) {
		final int startColumnPosition = mainHeader.getStartColumnPosition();
		final int endColumnPosition = startColumnPosition + mainHeader.getWidth() - 1;
		mainHeader.setEndColumnPosition(endColumnPosition);
	}

	@SuppressWarnings("ConstantConditions")
	private void alignDataHeadersRowPosition(List<Header> headers) {
		final OptionalInt maxRowPosition = headers
				.stream()
				.filter(Header::isOverData)
				.mapToInt(Header::getRowPosition)
				.max();
		headers.stream()
				.filter(Header::isOverData)
				.forEach(header ->
						header.setRowPosition(maxRowPosition.getAsInt()));
	}

	private int getFirstDataRowPosition(List<Header> headers) {
		return headers.stream()
				.filter(Header::isOverData)
				.findAny()
				.map(Header::getRowPosition)
				.orElseThrow(() ->
						new CoordinatesCalculateFail(NO_DATA_HEADERS));
	}

	public List<Header> getCalculatedHeaders() {
		return this.calculatedHeaders;
	}

	public int getFirstDataRowPosition() {
		return this.firstDataRowPosition;
	}

	private final class CoordinatesCalculateFail extends ExcelWriterException {
		CoordinatesCalculateFail(Messages message) {
			super(message.getMessage());
		}
	}
}