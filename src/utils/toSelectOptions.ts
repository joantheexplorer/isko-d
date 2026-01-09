const toSelectOptions = (
  data: Record<string,any>[],
  value: string,
  label: string
) => (Array.isArray(data) ? data : []).map((entry) => ({value: entry?.[value], label: entry?.[label]}));

export default toSelectOptions;
