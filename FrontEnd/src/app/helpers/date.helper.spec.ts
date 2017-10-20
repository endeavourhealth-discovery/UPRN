import {DateHelper} from './date.helper';

describe('DateHelper', () => {
  it('Can parse null', () => {
    const result = DateHelper.parse(null);
    expect(result).toEqual(DateHelper.NOT_KNOWN);
  });

  it('Can parse empty string', () => {
    const result = DateHelper.parse('');
    expect(result).toEqual(DateHelper.NOT_KNOWN);
  });

  it('Can parse invalid string', () => {
    const result = DateHelper.parse('Invalid');
    expect(result).toEqual(DateHelper.NOT_KNOWN);
  });
});
