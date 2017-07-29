package tk.aizydorczyk.util.header;

import tk.aizydorczyk.model.Header;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HeadersCoordinatesCalculator {
	public List<Header> calculate(List<Header> headers) {
		setColumnPositions(headers);
		setRowPositions(headers);
		return headers;
	}

	private void setRowPositions(List<Header> headers) {
		Header mainHeader = getMainHeader(headers);
		setBottomRowsPosition(mainHeader);


		Long startColumnPosition = mainHeader.getStartColumnPosition();
		Long endColumnPosition = startColumnPosition + mainHeader.getWidth() - 1L;
		mainHeader.setEndColumnPosition(endColumnPosition);
	}

	private void setBottomRowsPosition(Header header) {
		for (Header bottomHeader : header.getBottomHeaders()) {

			if (header.getRowPosition() == null){
				header.setRowPosition(0L);
			}

			bottomHeader.setRowPosition(header.getRowPosition() + 1);
			setBottomRowsPosition(bottomHeader);
		}
	}

	private void setColumnPositions(List<Header> headers) {
		setColumnPositionsOfDataHeaders(headers);
		setColumnPositionsOfRestHeadersByDataHeadersPositions(headers);
	}

	private void setColumnPositionsOfRestHeadersByDataHeadersPositions(List<Header> headers) {
		List<Header> notOverDataHeaders = headers.stream()
				.filter(Header::notOverData)
				.collect(Collectors.toList());

		setStartColumnPositions(notOverDataHeaders);
		setEndColumnPositions(notOverDataHeaders);
	}

	private void setEndColumnPositions(List<Header> notOverDataHeaders) {
		for (Header header : notOverDataHeaders) {
			long endColumnPosition = header.getStartColumnPosition() + header.getWidth();
			header.setEndColumnPosition(endColumnPosition);
		}
	}

	private void setStartColumnPositions(List<Header> notOverDataHeaders) {
		for (Header header : notOverDataHeaders) {
			if(header.getStartColumnPosition() == null){
				setStartColumnPositions(header);
			}
		}
	}

	private void setStartColumnPositions(Header header) {

		Header firstBottomHeader = header.getBottomHeaders().get(0);

		if (firstBottomHeader.getStartColumnPosition() == null){
			setStartColumnPositions(firstBottomHeader);
			header.setStartColumnPosition(firstBottomHeader.getStartColumnPosition());
		} else {
			header.setStartColumnPosition(firstBottomHeader.getStartColumnPosition());
		}
	}

	private void setColumnPositionsOfDataHeaders(List<Header> headers) {
		List<Header> dataHeaders = headers
				.stream()
				.filter(Header::isOverData)
				.collect(Collectors.toList());

		long columnIndex = 0L;
		for (Header dataHeader : dataHeaders) {
			dataHeader.setStartColumnPosition(columnIndex);
			dataHeader.setEndColumnPosition(columnIndex);
			columnIndex++;
		}
	}

	private Header getMainHeader(List<Header> headers) {
		Optional<Header> mainHeaderOptional = headers
				.stream()
				.filter(Header::isMainHeader)
				.findFirst();

		return mainHeaderOptional.orElseThrow(NoCorrectMainHeader::new);
	}

	private class NoCorrectMainHeader extends RuntimeException{

	}

}
