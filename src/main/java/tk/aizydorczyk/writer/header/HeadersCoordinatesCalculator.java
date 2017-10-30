package tk.aizydorczyk.writer.header;

import lombok.Getter;
import tk.aizydorczyk.enums.Messages;
import tk.aizydorczyk.model.Header;

import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static tk.aizydorczyk.common.utils.ParserUtils.selectMainHeaderOrThrow;
import static tk.aizydorczyk.enums.Messages.NO_BOTTOM_HEADERS;
import static tk.aizydorczyk.enums.Messages.NO_DATA_HEADERS;
import static tk.aizydorczyk.enums.Messages.NO_MAIN_HEADER;

@Getter
public class HeadersCoordinatesCalculator {

	private final List<Header> calculatedHeaders;
	private final long firstDataRowPosition;

	public HeadersCoordinatesCalculator(List<Header> headers) {
		this.calculatedHeaders = calculate(headers);
		this.firstDataRowPosition = getFirstDataRowPosition(headers);
	}

	private List<Header> calculate(List<Header> headers) {
		calculateColumnPositions(headers);

		final Header mainHeader = selectMainHeaderOrThrow(headers,
				() -> new CoordinatesCalculateFail(NO_MAIN_HEADER));

		calculateBottomRowsPosition(mainHeader);
		calculateMainHeaderColumnPosition(mainHeader);

		alignDataHeadersRowPosition(headers);
		return headers;
	}

	private void calculateColumnPositions(List<Header> headers) {
		calculateColumnPositionsOfDataHeaders(headers);
		calculateColumnPositionsOfRestHeadersByDataHeadersPositions(headers);
	}

	private void alignDataHeadersRowPosition(List<Header> headers) {
		final OptionalLong maxRowPosition = headers
				.stream()
				.filter(Header::isOverData)
				.mapToLong(Header::getRowPosition)
				.max();
		headers.stream()
				.filter(Header::isOverData)
				.forEach(header -> header.setRowPosition(maxRowPosition.getAsLong()));
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
		final long endColumnPosition = header.getStartColumnPosition() + header.getWidth() - 1L;
		header.setEndColumnPosition(endColumnPosition);
	}

	private void calculateMainHeaderColumnPosition(Header mainHeader) {
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

	private Long getFirstDataRowPosition(List<Header> headers) {
		return headers.stream()
				.filter(Header::isOverData)
				.findAny()
				.map(header -> header.getRowPosition())
				.orElseThrow(() -> new CoordinatesCalculateFail(NO_DATA_HEADERS));
	}

	private class CoordinatesCalculateFail extends RuntimeException {
		public CoordinatesCalculateFail(Messages message) {
			super(message.getMessage());
		}
	}


}
