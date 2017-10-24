package tk.aizydorczyk.processor.header;

import tk.aizydorczyk.enums.Messages;
import tk.aizydorczyk.model.Header;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static tk.aizydorczyk.common.utils.ParserUtils.selectMainHeaderOrThrow;
import static tk.aizydorczyk.enums.Messages.NO_BOTTOM_HEADERS;
import static tk.aizydorczyk.enums.Messages.NO_MAIN_HEADER;

public class HeadersCoordinatesCalculator {
	public List<Header> calculate(List<Header> headers) {
		calculateColumnPositions(headers);
		calculateRowPositions(headers);
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

		long columnIndex = 0L;
		for (final Header dataHeader : dataHeaders) {
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
		for (final Header header : notOverDataHeaders) {
			if (isNull(header.getStartColumnPosition())) {
				calculateStartColumnPositions(header);
			}
			calculateEndColumnPosition(header);
		}
	}

	private void calculateStartColumnPositions(Header header) {
		final Header firstBottomHeader = header.getBottomHeaders().stream()
				.findFirst()
				.orElseThrow(() -> new CoordinatesCalculateFail(NO_BOTTOM_HEADERS));

		if (isNull(firstBottomHeader.getStartColumnPosition())) {
			calculateStartColumnPositions(firstBottomHeader);
			header.setStartColumnPosition(firstBottomHeader.getStartColumnPosition());
		} else {
			header.setStartColumnPosition(firstBottomHeader.getStartColumnPosition());
		}
	}

	private void calculateEndColumnPosition(Header header) {
		final long endColumnPosition = header.getStartColumnPosition() + header.getWidth();
		header.setEndColumnPosition(endColumnPosition);
	}

	private void calculateRowPositions(List<Header> headers) {
		final Header mainHeader = selectMainHeaderOrThrow(headers,
				() -> new CoordinatesCalculateFail(NO_MAIN_HEADER));

		calculateBottomRowsPosition(mainHeader);

		final Long startColumnPosition = mainHeader.getStartColumnPosition();
		final Long endColumnPosition = startColumnPosition + mainHeader.getWidth() - 1L;
		mainHeader.setEndColumnPosition(endColumnPosition);
	}

	private void calculateBottomRowsPosition(Header header) {
		for (final Header bottomHeader : header.getBottomHeaders()) {
			if (isNull(header.getRowPosition())) {
				header.setRowPosition(0L);
			}

			bottomHeader.setRowPosition(header.getRowPosition() + 1);
			calculateBottomRowsPosition(bottomHeader);
		}
	}

	private class CoordinatesCalculateFail extends RuntimeException {
		public CoordinatesCalculateFail(Messages message) {
			super(message.getMessage());
		}
	}

}
