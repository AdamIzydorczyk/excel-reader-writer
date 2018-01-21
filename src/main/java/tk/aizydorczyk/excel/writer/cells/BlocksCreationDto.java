package tk.aizydorczyk.excel.writer.cells;

import tk.aizydorczyk.excel.common.model.Header;

import java.lang.reflect.Field;

final class BlocksCreationDto {
	Field field;
	Object untypedObject;
	Header header;

	private BlocksCreationDto(Field field, Object untypedObject, Header header) {
		this.field = field;
		this.untypedObject = untypedObject;
		this.header = header;
	}

	public static BlocksCreationDtoBuilder builder() {
		return new BlocksCreationDtoBuilder();
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BlocksCreationDto that = (BlocksCreationDto) o;

		return (field != null ? field.equals(that.field) : that.field == null)
				&& (untypedObject != null ? untypedObject.equals(that.untypedObject) : that.untypedObject == null)
				&& (header != null ? header.equals(that.header) : that.header == null);
	}

	@Override
	public int hashCode() {
		int result = field != null ? field.hashCode() : 0;
		result = 31 * result + (untypedObject != null ? untypedObject.hashCode() : 0);
		result = 31 * result + (header != null ? header.hashCode() : 0);
		return result;
	}

	public String toString() {
		return "BlocksCreationDto(field=" + this.field + ", untypedObject=" + this.untypedObject + ", header=" + this.header + ")";
	}

	public static class BlocksCreationDtoBuilder {
		private Field field;
		private Object untypedObject;
		private Header header;

		BlocksCreationDtoBuilder() {
		}

		public BlocksCreationDto.BlocksCreationDtoBuilder field(Field field) {
			this.field = field;
			return this;
		}

		public BlocksCreationDto.BlocksCreationDtoBuilder untypedObject(Object untypedObject) {
			this.untypedObject = untypedObject;
			return this;
		}

		public BlocksCreationDto.BlocksCreationDtoBuilder header(Header header) {
			this.header = header;
			return this;
		}

		public BlocksCreationDto build() {
			return new BlocksCreationDto(field, untypedObject, header);
		}

		public String toString() {
			return "BlocksCreationDto.BlocksCreationDtoBuilder(field=" + this.field + ", untypedObject=" + this.untypedObject + ", header=" + this.header + ")";
		}
	}
}