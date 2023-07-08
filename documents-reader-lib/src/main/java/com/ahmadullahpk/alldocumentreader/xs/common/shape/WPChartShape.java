package com.ahmadullahpk.alldocumentreader.xs.common.shape;

import com.ahmadullahpk.alldocumentreader.xs.thirdpart.achartengine.chart.AbstractChart;

public class WPChartShape extends WPAutoShape
{

	public AbstractChart getAChart() 
	{
		return chart;
	}

	public void setAChart(AbstractChart chart) 
	{
		this.chart = chart;
	}
	
	private AbstractChart chart;
}
